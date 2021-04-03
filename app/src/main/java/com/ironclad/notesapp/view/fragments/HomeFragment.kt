package com.ironclad.notesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ironclad.notesapp.R
import com.ironclad.notesapp.databinding.FragmentHomeBinding
import com.ironclad.notesapp.view.viewmodels.HomeViewModel
import com.ironclad.notesapp.view.viewmodels.HomeViewModelFactory

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelFactory: HomeViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = HomeViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
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

        viewModel.allNotes.observe({ lifecycle }) { notes ->
            if (notes.isEmpty()) {
                binding?.tvNoNotes?.visibility = View.VISIBLE
                binding?.recyclerViewNotes?.visibility = View.GONE
            } else {
                binding?.tvNoNotes?.visibility = View.GONE
                binding?.recyclerViewNotes?.visibility = View.VISIBLE
            }
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
}