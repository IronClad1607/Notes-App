package com.ironclad.notesapp.di

import android.content.Context
import androidx.room.Room
import com.ironclad.notesapp.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesNoteDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(context, NoteDatabase::class.java, NoteDatabase.DB_NAME).build()
    }

    @Singleton
    @Provides
    fun providesNoteDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()
}