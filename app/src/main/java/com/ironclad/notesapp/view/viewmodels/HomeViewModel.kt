package com.ironclad.notesapp.view.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.ironclad.notesapp.data.models.Note
import com.ironclad.notesapp.data.repos.NoteRepo
import kotlinx.coroutines.launch

class HomeViewModel(context: Context) : ViewModel() {
    private var repo = NoteRepo(context)

    private val _allNotes = MutableLiveData<List<Note>>()
    val allNotes: LiveData<List<Note>> = _allNotes

    fun getAllNotes() = viewModelScope.launch {
        val value = repo.getAllNote()?.value

        _allNotes.postValue(value ?: listOf())
    }
}


class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(context) as T
    }
}