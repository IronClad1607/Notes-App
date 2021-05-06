package com.ironclad.notesapp.view.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ironclad.notesapp.R
import com.ironclad.notesapp.data.NoteDatabase
import com.ironclad.notesapp.data.models.Note
import com.ironclad.notesapp.data.repos.NoteRepo
import com.ironclad.notesapp.databinding.FragmentNoteBinding
import com.ironclad.notesapp.view.viewmodels.NoteViewModel
import com.ironclad.notesapp.view.viewmodels.NoteViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteFragment : Fragment() {
    private var binding: FragmentNoteBinding? = null
    private val args: NoteFragmentArgs by navArgs()

    private lateinit var viewModel: NoteViewModel
    private lateinit var viewModelFactory: NoteViewModelFactory
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var repo: NoteRepo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        noteDatabase = NoteDatabase(requireContext())
        repo = NoteRepo(noteDatabase)
        viewModelFactory = NoteViewModelFactory(repo)
        viewModel = ViewModelProvider(this, viewModelFactory)[NoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId = args.noteId

        viewModel.getNote(noteId).observe(requireActivity(), { note ->
            inflateValues(note)
        })

    }

    private fun handleMenu(note: Note) {
        binding?.toolbarNote?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_delete -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        viewModel.deleteNote(note).also {
                            Toast.makeText(requireContext(), "Note Deleted", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigateUp()
                        }
                    }
                    true
                }
                R.id.item_edit -> {
                    findNavController().navigate(NoteFragmentDirections.goToEditNote())
                    true
                }
                else -> false
            }
        }
    }

    private fun setColor(color: Int): Drawable? {
        return ContextCompat.getDrawable(requireContext(), color)
    }

    private fun inflateValues(note: Note?) {
        note?.let { handleMenu(it) }

        binding?.toolbarNote?.title = note?.title
        binding?.textViewContent?.text = note?.message
        binding?.toolbarNote?.background = when (note?.label) {
            "Passwords" -> setColor(R.color.violet)
            "Shopping List" -> setColor(R.color.blue)
            "Task" -> setColor(R.color.red)
            "Messages" -> setColor(R.color.yellow)
            "List" -> setColor(R.color.indigo)
            "Ideas" -> setColor(R.color.green)
            "Other" -> setColor(R.color.orange)
            else -> setColor(R.color.notes_green)
        }

        binding?.textViewUpdated?.text = note?.updateAt
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}