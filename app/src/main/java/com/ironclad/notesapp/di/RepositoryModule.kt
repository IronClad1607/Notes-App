package com.ironclad.notesapp.di

import com.ironclad.notesapp.data.db.dao.NoteDao
import com.ironclad.notesapp.data.db.repos.NoteRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    fun provideNoteRepository(noteDao: NoteDao) = NoteRepositoryImp(noteDao)
}