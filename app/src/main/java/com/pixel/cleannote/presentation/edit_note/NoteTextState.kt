package com.pixel.cleannote.presentation.edit_note

data class NoteTextState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)