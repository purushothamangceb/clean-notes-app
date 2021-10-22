package com.pixel.cleannote.domain.usecase

data class NoteUseCases(
    val getNote: GetNote,
    val addNote: AddNote,
    val getNoteList: GetNoteList,
    val deleteNote: DeleteNote,
    val updateNote: UpdateNote
) {
}