package com.pixel.cleannote.domain.usecase

import com.pixel.cleannote.domain.model.InvalidNoteException
import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(noteRepository: NoteRepository) : AbstractUseCase(noteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title is empty")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Content is empty")
        }
        repository.insert(note)
    }
}