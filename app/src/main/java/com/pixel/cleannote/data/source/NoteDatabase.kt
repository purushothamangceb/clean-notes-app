package com.pixel.cleannote.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pixel.cleannote.domain.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object {
        const val DATABASE_NAME = "clean_notes_db"
    }
}