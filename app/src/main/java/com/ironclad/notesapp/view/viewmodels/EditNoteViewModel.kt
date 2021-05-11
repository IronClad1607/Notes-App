package com.ironclad.notesapp.view.viewmodels

import androidx.lifecycle.ViewModel
import com.ironclad.notesapp.data.db.models.Note
import com.ironclad.notesapp.data.db.repos.NoteRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(private val repository: NoteRepositoryImp) : ViewModel() {
    fun getANote(id: Long) = repository.getANote(id)

    suspend fun editNote(note: Note) = repository.updateNote(note)
}
