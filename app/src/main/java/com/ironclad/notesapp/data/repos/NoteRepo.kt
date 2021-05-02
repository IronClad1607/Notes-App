package com.ironclad.notesapp.data.repos

import androidx.lifecycle.LiveData
import com.ironclad.notesapp.data.NoteDatabase
import com.ironclad.notesapp.data.models.Note

class NoteRepo(private val noteDatabase: NoteDatabase) {

    suspend fun addNote(note: Note) = noteDatabase.noteDao().addNote(note)
    suspend fun updateNote(note: Note) = noteDatabase.noteDao().updateNote(note)
    suspend fun deleteNote(note: Note) = noteDatabase.noteDao().deleteNote(note)
    fun getAllNotes(): LiveData<List<Note>> = noteDatabase.noteDao().getAllNotes()
    fun getANote(id: Long): LiveData<Note> = noteDatabase.noteDao().getANote(id)
}