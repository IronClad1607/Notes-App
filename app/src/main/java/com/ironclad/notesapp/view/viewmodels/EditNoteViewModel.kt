package com.ironclad.notesapp.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ironclad.notesapp.data.models.Note
import com.ironclad.notesapp.data.repos.NoteRepo

class EditNoteViewModel(private val repo: NoteRepo) : ViewModel() {
    fun getANote(id: Long) = repo.getANote(id)

    suspend fun editNote(note: Note) = repo.updateNote(note)
}


@Suppress("UNCHECKED_CAST")
class EditNoteViewModelFactory(private val repo: NoteRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditNoteViewModel(repo) as T
    }

}