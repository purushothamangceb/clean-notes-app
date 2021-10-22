package com.pixel.cleannote.presentation.notelist

import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.util.OrderByType
import com.pixel.cleannote.domain.util.SortBy

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val sortBy: SortBy = SortBy.Date(OrderByType.Descending),
    val isSortBySectionVisible: Boolean = false
) {
}