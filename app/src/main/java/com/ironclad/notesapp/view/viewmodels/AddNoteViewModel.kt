package com.ironclad.notesapp.view.viewmodels

import androidx.lifecycle.ViewModel
import com.ironclad.notesapp.data.models.Note
import com.ironclad.notesapp.data.repos.NoteRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(private val repository: NoteRepositoryImp) :
    ViewModel() {
    suspend fun insertNote(note: Note) = repository.addNote(note)
}
