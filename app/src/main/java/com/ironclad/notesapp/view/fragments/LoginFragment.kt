package com.ironclad.notesapp.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ironclad.notesapp.databinding.FragmentLoginBinding
import com.ironclad.notesapp.utils.Constants.Companion.ERROR_TAG
import com.ironclad.notesapp.utils.SharedPreferenceHelper
import com.ironclad.notesapp.view.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private lateinit var preferenceManager: SharedPreferenceHelper
    private lateinit var handleSignIn: ActivityResultLauncher<Intent>

    private val viewModel by viewModels<LoginViewModel>()

    @Inject
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = SharedPreferenceHelper.getInstance(requireContext())

        handleSignIn =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleSignInResult(task)
                }
            }
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

        binding?.btnSkipLogin?.setOnClickListener {
            preferenceManager.loginSkipped = true
            findNavController().navigate(LoginFragmentDirections.goToHomeFromLogin())
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        handleSignIn.launch(signInIntent)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            preferenceManager.apply {
                userLoggedIn = true
                loginSkipped = false
            }
            firebaseAuthWithGoogle(account!!)
        } catch (e: ApiException) {
            Log.e(ERROR_TAG, e.localizedMessage ?: "Failed")
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        viewModel.signInWithGoogle(account)

        viewModel.success.observe(requireActivity(), { success ->
            if (success) {
                Toast.makeText(requireContext(), "Login In Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(LoginFragmentDirections.goToHomeFromLogin())
            } else {
                Toast.makeText(requireContext(), "Login In Failed", Toast.LENGTH_SHORT).show()
            }

        })
    }
}