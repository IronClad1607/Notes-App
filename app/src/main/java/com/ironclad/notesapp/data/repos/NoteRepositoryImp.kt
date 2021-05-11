package com.ironclad.notesapp.data.repos

import androidx.lifecycle.LiveData
import com.ironclad.notesapp.data.dao.NoteDao
import com.ironclad.notesapp.data.models.Note
import javax.inject.Inject

class NoteRepositoryImp @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()
    override fun getANote(id: Long): LiveData<Note> = noteDao.getANote(id)
    override suspend fun addNote(note: Note): Long = noteDao.addNote(note)
    override suspend fun updateNote(note: Note) = noteDao.updateNote(note)
    override suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)
    override suspend fun clearDb() = noteDao.clearDb()
}