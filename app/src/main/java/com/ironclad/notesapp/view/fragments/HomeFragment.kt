package com.ironclad.notesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
            //todo nav to add fragment
            Toast.makeText(requireContext(), "Add Click", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleMenu() {
        binding?.toolbarHome?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_profile -> {
                    //todo nav to profile fragment
                    Toast.makeText(requireContext(), "Profile Click", Toast.LENGTH_SHORT).show()
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