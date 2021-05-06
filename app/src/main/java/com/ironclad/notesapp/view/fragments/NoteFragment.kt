package com.ironclad.notesapp.view.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ironclad.notesapp.R
import com.ironclad.notesapp.databinding.FragmentNoteBinding

class NoteFragment : Fragment() {
    private var binding: FragmentNoteBinding? = null
    private val args: NoteFragmentArgs by navArgs()

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
        handleMenu()
        val note = args.note

        binding?.toolbarNote?.title = note.title
        binding?.textViewContent?.text = note.message
        binding?.toolbarNote?.background = when (note.label) {
            "Passwords" -> setColor(R.color.violet)
            "Shopping List" -> setColor(R.color.blue)
            "Task" -> setColor(R.color.red)
            "Messages" -> setColor(R.color.yellow)
            "List" -> setColor(R.color.indigo)
            "Ideas" -> setColor(R.color.green)
            "Other" -> setColor(R.color.orange)
            else -> setColor(R.color.notes_green)
        }

        binding?.textViewUpdated?.text = note.updateAt
    }

    private fun handleMenu() {
        binding?.toolbarNote?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.item_delete -> {
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

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}