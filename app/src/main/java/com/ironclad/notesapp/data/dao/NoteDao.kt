package com.ironclad.notesapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ironclad.notesapp.data.models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note): Long

    @Query("SELECT * FROM notes_table ORDER BY priority ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id=:id")
    fun getANote(id: Long): LiveData<Note>

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}