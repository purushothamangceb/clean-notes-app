package com.pixel.cleannote.domain.usecase

import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.repository.NoteRepository
import com.pixel.cleannote.domain.util.OrderByType
import com.pixel.cleannote.domain.util.SortBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNoteList(noteRepository: NoteRepository) : AbstractUseCase(noteRepository) {
    operator fun invoke(sortBy: SortBy): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (sortBy.orderByType) {
                is OrderByType.Ascending -> {
                    when (sortBy) {
                        is SortBy.Title -> notes.sortedBy { it.title.lowercase() }
                        is SortBy.Date -> notes.sortedBy { it.modifiedOn }
                        is SortBy.BackgroundColor -> notes.sortedBy { it.backgroundColor }
                    }
                }
                is OrderByType.Descending -> {
                    when (sortBy) {
                        is SortBy.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is SortBy.Date -> notes.sortedByDescending { it.modifiedOn }
                        is SortBy.BackgroundColor -> notes.sortedByDescending { it.backgroundColor }
                    }
                }
            }
        }
    }
}