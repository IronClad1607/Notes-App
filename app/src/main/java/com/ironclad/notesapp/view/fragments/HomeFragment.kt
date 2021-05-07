package com.ironclad.notesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ironclad.notesapp.R
import com.ironclad.notesapp.databinding.FragmentHomeBinding
import com.ironclad.notesapp.utils.extensions.getNoOfColumns
import com.ironclad.notesapp.view.adapter.NotesAdapter
import com.ironclad.notesapp.view.adapter.OnItemClickListener
import com.ironclad.notesapp.view.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener {

    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAdapter = NotesAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleMenu()
        viewModel.getAllNotes()

        binding?.buttonAdd?.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.goToAddANote())
        }

        binding?.recyclerViewNotes?.apply {
            layoutManager =
                GridLayoutManager(requireContext(), getNoOfColumns(150, requireContext()))
            adapter = mAdapter
        }

        showData()
    }

    private fun showData() {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getAllNotes().observe(requireActivity(), { notes ->
                if (notes.isEmpty()) {
                    binding?.tvNoNotes?.visibility = View.VISIBLE
                    binding?.recyclerViewNotes?.visibility = View.GONE
                } else {
                    binding?.tvNoNotes?.visibility = View.GONE
                    binding?.recyclerViewNotes?.visibility = View.VISIBLE
                    mAdapter.submitList(notes)
                }

            })
        }
    }

    private fun handleMenu() {
        binding?.toolbarHome?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_profile -> {
                    findNavController().navigate(HomeFragmentDirections.goToProfile())
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onItemClick(id: Long) {
        findNavController().navigate(HomeFragmentDirections.goToNote(id))
    }
}