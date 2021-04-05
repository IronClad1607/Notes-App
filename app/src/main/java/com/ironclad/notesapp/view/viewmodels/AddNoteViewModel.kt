package com.ironclad.notesapp.view.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.ironclad.notesapp.data.models.Note
import com.ironclad.notesapp.data.repos.NoteRepo
import kotlinx.coroutines.launch

class AddNoteViewModel(private val repo: NoteRepo) : ViewModel() {
    suspend fun insertNote(note: Note) = repo.addNote(note)
}

@Suppress("UNCHECKED_CAST")
class AddNoteViewModelFactory(private val repo: NoteRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddNoteViewModel(repo) as T
    }

}