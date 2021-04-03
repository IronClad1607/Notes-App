package com.ironclad.notesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ironclad.notesapp.R
import com.ironclad.notesapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

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

        binding?.buttonAdd?.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.goToAddANote())
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