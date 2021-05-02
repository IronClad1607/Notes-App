package com.ironclad.notesapp.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ironclad.notesapp.R
import com.ironclad.notesapp.data.NoteDatabase
import com.ironclad.notesapp.data.models.Note
import com.ironclad.notesapp.data.repos.NoteRepo
import com.ironclad.notesapp.databinding.FragmentAddNoteBinding
import com.ironclad.notesapp.utils.Constants.Companion.ERROR_TAG
import com.ironclad.notesapp.utils.extensions.getCurrentTime
import com.ironclad.notesapp.view.viewmodels.AddNoteViewModel
import com.ironclad.notesapp.view.viewmodels.AddNoteViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddNoteBinding? = null
    private val priorityItems = listOf("1", "2", "3", "4", "5")
    private val labelItems =
        listOf("Passwords", "Shopping List", "Task", "Messages", "List", "Ideas", "Other")
    private var priority = ""
    private var label = ""

    private lateinit var viewModel: AddNoteViewModel
    private lateinit var viewModelFactory: AddNoteViewModelFactory
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var repo: NoteRepo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteDatabase = NoteDatabase(requireContext())
        repo = NoteRepo(noteDatabase)
        viewModelFactory = AddNoteViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory)[AddNoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)

        dialog.setOnShowListener { dialog ->
            val bottomSheetDialog = dialog as BottomSheetDialog
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priorityAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, priorityItems)
        val labelAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, labelItems)
        (binding?.dropdownPriority?.editText as AutoCompleteTextView).setAdapter(priorityAdapter)
        (binding?.dropdownLabel?.editText as AutoCompleteTextView).setAdapter(labelAdapter)

        binding?.close?.setOnClickListener {
            findNavController().navigateUp()
        }

        (binding?.dropdownPriority?.editText as AutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->
            priority = priorityItems[position]
        }

        (binding?.dropdownLabel?.editText as AutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->
            label = labelItems[position]
        }

        binding?.buttonSave?.setOnClickListener {
            val title = binding?.editTextTitle?.editText?.text?.toString() ?: ""
            val message = binding?.editTextMessage?.editText?.text?.toString() ?: ""
            val createdAt = getCurrentTime()
            val note = Note(title, message, createdAt, createdAt, priority.toInt(), label)


            CoroutineScope(Dispatchers.Main).launch {
                viewModel.insertNote(note).also { id ->
                    if (id > 0) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    } else {
                        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                        Log.e(ERROR_TAG, "FAILED")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun setUpFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}