package com.pixel.cleannote.domain.repository

import com.pixel.cleannote.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNote(id: Int): Note?

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)

    suspend fun update(note: Note)

}