package com.pixel.cleannote.data.repository

import com.pixel.cleannote.data.source.NoteDao
import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override suspend fun getNote(id: Int): Note? {
        return noteDao.getNote(id)
    }

    override suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    override suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

    override suspend fun update(note: Note) {
        noteDao.update(note)
    }
}

