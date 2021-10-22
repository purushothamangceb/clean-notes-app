package com.pixel.cleannote.domain.usecase

import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.repository.NoteRepository

class GetNote(repository: NoteRepository) : AbstractUseCase(repository) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNote(id)
    }
}