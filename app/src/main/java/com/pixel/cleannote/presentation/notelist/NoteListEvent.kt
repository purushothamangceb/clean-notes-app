package com.pixel.cleannote.presentation.notelist

import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.util.SortBy

sealed class NoteListEvent {
    data class SortByEvent(val sortBy: SortBy) : NoteListEvent()
    data class DeleteNote(val note: Note) : NoteListEvent()
    object RestoreNote : NoteListEvent()
    object ToggleSortBySection : NoteListEvent()
}
