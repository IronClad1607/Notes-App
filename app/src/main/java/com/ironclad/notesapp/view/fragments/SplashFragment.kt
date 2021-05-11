package com.ironclad.notesapp.view.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ironclad.notesapp.databinding.FragmentSplashBinding
import com.ironclad.notesapp.utils.Constants.Companion.SPLASH_DELAY
import com.ironclad.notesapp.utils.SharedPreferenceHelper

class SplashFragment : Fragment() {

    private var binding: FragmentSplashBinding? = null
    private lateinit var preferenceManager: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = SharedPreferenceHelper.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        Handler().postDelayed({
            if (preferenceManager.userLoggedIn || preferenceManager.loginSkipped){
                findNavController().navigate(SplashFragmentDirections.goToHomeFromSplash())
            }else{
                findNavController().navigate(SplashFragmentDirections.goToLoginScreen())
            }
        }, SPLASH_DELAY)

        return binding?.root
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}