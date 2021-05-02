package com.ironclad.notesapp.view.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.ironclad.notesapp.data.models.Note
import com.ironclad.notesapp.data.repos.NoteRepo
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: NoteRepo) : ViewModel() {
    fun getAllNotes() = repo.getAllNotes()

    fun getANote(id: Long) = repo.getANote(id)
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repo: NoteRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}