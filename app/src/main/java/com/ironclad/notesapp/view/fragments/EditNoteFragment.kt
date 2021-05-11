package com.ironclad.notesapp.view.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ironclad.notesapp.R
import com.ironclad.notesapp.data.db.models.Note
import com.ironclad.notesapp.databinding.FragmentEditNoteBinding
import com.ironclad.notesapp.utils.extensions.getCurrentTime
import com.ironclad.notesapp.utils.extensions.toEditable
import com.ironclad.notesapp.view.viewmodels.EditNoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditNoteFragment : BottomSheetDialogFragment() {
    private var binding: FragmentEditNoteBinding? = null
    private val args: EditNoteFragmentArgs by navArgs()

    private val viewModel by viewModels<EditNoteViewModel>()

    private val priorityItems = listOf("1", "2", "3", "4", "5")
    private val labelItems =
        listOf("Passwords", "Shopping List", "Task", "Messages", "List", "Ideas", "Other")
    private var priorityGlobal = ""
    private var labelGlobal = ""
    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val note = args.note

        inflateValues(note)

        binding?.close?.setOnClickListener {
            findNavController().navigateUp()
        }


        val priorityAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, priorityItems)
        val labelAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, labelItems)
        (binding?.dropdownPriority?.editText as AutoCompleteTextView).setAdapter(priorityAdapter)
        (binding?.dropdownLabel?.editText as AutoCompleteTextView).setAdapter(labelAdapter)


        (binding?.dropdownPriority?.editText as AutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->
            priorityGlobal = priorityItems[position]
        }

        (binding?.dropdownLabel?.editText as AutoCompleteTextView).setOnItemClickListener { _, _, position, _ ->
            labelGlobal = labelItems[position]
        }

        binding?.buttonSave?.setOnClickListener {
            val title = binding?.editTextTitle?.editText?.text?.toString() ?: ""
            val message = binding?.editTextMessage?.editText?.text?.toString() ?: ""
            val createdAt = note.createdAt
            val updatedAt = getCurrentTime()
            val id = note.id
            val label = if (labelGlobal.isBlank()) note.label else labelGlobal
            val priority = if (priorityGlobal.isBlank()) note.priority else priorityGlobal.toInt()
            val noteEdited = Note(title, message, createdAt, updatedAt, priority, label, id)

            CoroutineScope(Dispatchers.Main).launch {
                viewModel.editNote(noteEdited).also {
                    Toast.makeText(requireContext(), "Note Updated", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }
            }
        }
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

    private fun setUpFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun inflateValues(note: Note?) {
        binding?.editTextTitle?.editText?.text = note?.title?.toEditable()
        binding?.editTextMessage?.editText?.text = note?.message?.toEditable()
        (binding?.dropdownPriority?.editText as AutoCompleteTextView).setText(
            note?.priority?.toString(),
            false
        )
        (binding?.dropdownLabel?.editText as AutoCompleteTextView).setText(note?.label, false)
    }
}