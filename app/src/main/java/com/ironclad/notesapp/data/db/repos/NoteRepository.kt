package com.ironclad.notesapp.data.db.repos

import androidx.lifecycle.LiveData
import com.ironclad.notesapp.data.db.models.Note

interface NoteRepository {
    fun getAllNotes(): LiveData<List<Note>>
    fun getANote(id: Long): LiveData<Note>
    suspend fun addNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun clearDb()
}