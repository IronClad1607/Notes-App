package com.ironclad.notesapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ironclad.notesapp.databinding.FragmentLoginBinding
import com.ironclad.notesapp.utils.Constants.Companion.ERROR_TAG
import com.ironclad.notesapp.utils.SharedPreferenceHelper

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var preferenceManager: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = SharedPreferenceHelper.getInstance(requireContext())

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSignIn?.apply {
            this.setSize(SignInButton.SIZE_WIDE)
            this.setColorScheme(SignInButton.COLOR_AUTO)
        }

        binding?.btnSignIn?.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            preferenceManager.userLoggedIn = true
            Toast.makeText(requireContext(), "User Logged In Successfully", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(LoginFragmentDirections.goToHomeFromLogin())
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), "Log In failed.", Toast.LENGTH_SHORT).show()
            Log.e(ERROR_TAG, e.localizedMessage ?: "Failed")
        }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }
}