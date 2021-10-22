package com.pixel.cleannote.domain.usecase

import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.repository.NoteRepository

class UpdateNote(repository: NoteRepository) : AbstractUseCase(repository) {

    suspend operator fun invoke(note: Note) {
        repository.update(note)
    }

}