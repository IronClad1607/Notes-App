package com.ironclad.notesapp.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ironclad.notesapp.data.models.Note
import com.ironclad.notesapp.data.repos.NoteRepo

class NoteViewModel(private val repo: NoteRepo) : ViewModel() {
    suspend fun deleteNote(note: Note) = repo.deleteNote(note)

    fun getNote(id:Long) = repo.getANote(id)
}

@Suppress("UNCHECKED_CAST")
class NoteViewModelFactory(private val repo: NoteRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoteViewModel(repo) as T
    }

}