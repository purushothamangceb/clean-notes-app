package com.pixel.cleannote.presentation.notelist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.domain.usecase.NoteUseCases
import com.pixel.cleannote.domain.util.OrderByType
import com.pixel.cleannote.domain.util.SortBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(private val noteUseCases: NoteUseCases) : ViewModel() {

    private var _state = mutableStateOf(NoteListState())
    val state: State<NoteListState> = _state
    private var lastDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(SortBy.Date(OrderByType.Descending))
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.SortByEvent -> {
                getNotes(event.sortBy)
            }
            is NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    lastDeletedNote = event.note
                }
            }
            is NoteListEvent.RestoreNote -> {
                viewModelScope.launch {
                    lastDeletedNote?.let {
                        noteUseCases.addNote(it)
                        lastDeletedNote = null
                    }
                }
            }
            is NoteListEvent.ToggleSortBySection -> {
                _state.value = _state.value.copy(
                    isSortBySectionVisible = !_state.value.isSortBySectionVisible
                )
            }
        }
    }

    private fun getNotes(sortBy: SortBy) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNoteList(sortBy).onEach { notes ->
            _state.value = _state.value.copy(
                notes = notes, sortBy = sortBy
            )
        }.launchIn(viewModelScope)
    }
}