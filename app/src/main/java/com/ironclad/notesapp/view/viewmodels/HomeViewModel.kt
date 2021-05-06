package com.ironclad.notesapp.view.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ironclad.notesapp.data.repos.NoteRepo

class HomeViewModel(private val repo: NoteRepo) : ViewModel() {
    fun getAllNotes() = repo.getAllNotes()
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repo: NoteRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}