package com.pixel.cleannote.domain.usecase

import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.repository.NoteRepository

class DeleteNote(repository: NoteRepository) : AbstractUseCase(repository) {

    suspend operator fun invoke(note: Note) {
        repository.delete(note)
    }
}