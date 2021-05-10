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
import com.ironclad.notesapp.utils.SharedPreferenceHelper
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
    private lateinit var preferenceManager: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = SharedPreferenceHelper.getInstance(requireContext())
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
        handleMenuClick()
        handleMenuInflate()
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

    private fun handleMenuInflate() {
        if (preferenceManager.loginSkipped && !preferenceManager.userLoggedIn) {
            binding?.toolbarHome?.menu?.clear()
            binding?.toolbarHome?.inflateMenu(R.menu.toolbar_menu_home_skipped)
        } else if (preferenceManager.userLoggedIn && !preferenceManager.loginSkipped) {
            binding?.toolbarHome?.menu?.clear()
            binding?.toolbarHome?.inflateMenu(R.menu.toolbar_menu_home_logged_in)
        }
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

    private fun handleMenuClick() {
        binding?.toolbarHome?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_profile -> {
                    findNavController().navigate(HomeFragmentDirections.goToProfile())
                    true
                }
                R.id.item_login -> {
                    findNavController().navigate(HomeFragmentDirections.goToLogin())
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