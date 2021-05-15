package com.ironclad.notesapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ironclad.notesapp.data.db.dao.NoteDao
import com.ironclad.notesapp.data.db.models.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DB_NAME = "notes_database.db"
    }
}