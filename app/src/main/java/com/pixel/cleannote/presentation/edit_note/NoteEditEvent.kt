package com.pixel.cleannote.presentation.edit_note

import androidx.compose.ui.focus.FocusState

sealed class NoteEditEvent {
    data class TitleEnteredEvent(val title: String) : NoteEditEvent()
    data class ContentEnteredEvent(val content: String) : NoteEditEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : NoteEditEvent()
    data class ChangeContentFocus(val focusState: FocusState) : NoteEditEvent()
    data class ChangeBackgroundColor(val color: Int) : NoteEditEvent()
    object SaveNote : NoteEditEvent()
}