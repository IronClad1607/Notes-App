package com.ironclad.notesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
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
        val note = args.note

        binding?.toolbarNote?.title = note.title
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}