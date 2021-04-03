package com.ironclad.notesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ironclad.notesapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}