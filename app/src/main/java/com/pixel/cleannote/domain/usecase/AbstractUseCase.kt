package com.pixel.cleannote.domain.usecase

import com.pixel.cleannote.domain.repository.NoteRepository

abstract class AbstractUseCase(protected val repository: NoteRepository)