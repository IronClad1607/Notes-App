package com.ironclad.notesapp.data.repos

import android.content.Context
import com.ironclad.notesapp.data.NoteDatabase
import com.ironclad.notesapp.data.dao.NoteDao
import com.ironclad.notesapp.data.models.Note

class NoteRepo(context: Context) {

    private var notesDao: NoteDao?

    init {
        val db = NoteDatabase.getDatabase(context)
        notesDao = db?.noteDao()
    }

    suspend fun getAllNote() = notesDao?.getAllNotes()
    suspend fun getNote(id: Long) = notesDao?.getANote(id)
    suspend fun addNote(note: Note) = notesDao?.addNote(note)
    suspend fun deleteNote(note: Note) = notesDao?.deleteNote(note)
    suspend fun updateNote(note: Note) = notesDao?.updateNote(note)
}