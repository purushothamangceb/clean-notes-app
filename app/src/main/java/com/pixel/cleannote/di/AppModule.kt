package com.pixel.cleannote.di

import android.app.Application
import androidx.room.Room
import com.pixel.cleannote.data.repository.NoteRepositoryImpl
import com.pixel.cleannote.data.source.NoteDatabase
import com.pixel.cleannote.domain.repository.NoteRepository
import com.pixel.cleannote.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(application: Application): NoteDatabase {
        return Room.databaseBuilder(
            application,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDatabase.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            GetNote(noteRepository),
            AddNote(noteRepository),
            GetNoteList(noteRepository),
            DeleteNote(noteRepository),
            UpdateNote(noteRepository)
        )
    }
}