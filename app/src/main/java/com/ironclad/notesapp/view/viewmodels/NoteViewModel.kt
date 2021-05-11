package com.ironclad.notesapp.view.viewmodels

import androidx.lifecycle.ViewModel
import com.ironclad.notesapp.data.db.models.Note
import com.ironclad.notesapp.data.db.repos.NoteRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepositoryImp) :
    ViewModel() {
    suspend fun deleteNote(note: Note) = repository.deleteNote(note)

    fun getNote(id: Long) = repository.getANote(id)
}
