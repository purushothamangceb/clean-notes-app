package com.pixel.cleannote.presentation.edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixel.cleannote.domain.model.InvalidNoteException
import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitle =
        mutableStateOf(NoteTextState(hint = "Enter Title here"))
    val noteTitle: State<NoteTextState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextState(hint = "Note content..."))
    val noteContent: State<NoteTextState> = _noteContent

    private val _backgroundColor = mutableStateOf(Note.backgroundColors.random())
    val backgroundColor: State<Int> = _backgroundColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = _noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = _noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _backgroundColor.value = note.backgroundColor
                    }
                }
            }
        }
    }

    fun onEvent(event: NoteEditEvent) {
        when (event) {
            is NoteEditEvent.TitleEnteredEvent -> {
                _noteTitle.value = _noteTitle.value.copy(
                    text = event.title
                )
            }
            is NoteEditEvent.ContentEnteredEvent -> {
                _noteContent.value = _noteContent.value.copy(
                    text = event.content
                )
            }
            is NoteEditEvent.ChangeTitleFocus -> {
                _noteTitle.value = _noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is NoteEditEvent.ChangeContentFocus -> {
                _noteContent.value = _noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is NoteEditEvent.ChangeBackgroundColor -> {
                _backgroundColor.value = event.color
            }
            is NoteEditEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                backgroundColor = backgroundColor.value,
                                createdOn = System.currentTimeMillis(),
                                id = currentNoteId,
                                modifiedOn = System.currentTimeMillis()
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(message = e.message.toString()))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}