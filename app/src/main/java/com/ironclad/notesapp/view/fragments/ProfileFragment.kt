package com.ironclad.notesapp.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ironclad.notesapp.databinding.FragmentProfileBinding
import com.ironclad.notesapp.utils.SharedPreferenceHelper
import com.ironclad.notesapp.utils.extensions.setUpFullHeight
import com.ironclad.notesapp.view.viewmodels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BottomSheetDialogFragment() {

    private var binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var preferenceManager: SharedPreferenceHelper

    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        preferenceManager = SharedPreferenceHelper.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = auth.currentUser



        binding?.apply {
            imageViewProfilePicture.load(user?.photoUrl)
            textViewEmail.text = user?.email
            textViewName.text = user?.displayName

            close.setOnClickListener {
                findNavController().navigateUp()
            }

            btnLogOut.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.clearDb().also {
                        signOut()
                    }
                }
            }
        }
    }

    private fun signOut() {
        auth.signOut()
        preferenceManager.userLoggedIn = false
        findNavController().navigate(ProfileFragmentDirections.goToLoginFromProfile())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)

        dialog.setOnShowListener { dialogInner ->
            val bottomSheetDialog = dialogInner as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheetDialog.setCancelable(false)

            parentLayout?.let {
                val behavior = BottomSheetBehavior.from(it)
                setUpFullHeight(it)
                behavior.isHideable = false
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return dialog
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}