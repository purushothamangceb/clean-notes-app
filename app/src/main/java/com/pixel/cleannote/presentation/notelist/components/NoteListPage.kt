package com.pixel.cleannote.presentation.notelist.components

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.TextRotateVertical
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pixel.cleannote.presentation.notelist.NoteListEvent
import com.pixel.cleannote.presentation.notelist.NoteListViewModel
import com.pixel.cleannote.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NoteListPage(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.value
    val scaffoldSate = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate(Screen.EditNoteScreen.route) },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add New Note")
        }
    }, scaffoldState = scaffoldSate) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Notes")
                Row {
                    IconButton(onClick = { viewModel.onEvent(NoteListEvent.ToggleSortBySection) }) {
                        Icon(imageVector = Icons.Default.TextRotateVertical, contentDescription = "Sort")
                    }
                    IconButton(onClick = { viewModel.onEvent(NoteListEvent.ToggleSortBySection) }) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                    }
                }
            }
            AnimatedVisibility(
                visible = state.isSortBySectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortBySection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    sortBy = state.sortBy,
                    onSortBy = { viewModel.onEvent(NoteListEvent.SortByEvent(it)) })
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.notes) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.EditNoteScreen.route +
                                            "?noteId=${note.id}&noteColor=${note.backgroundColor}"
                                )
                            },
                        onDeleteIconClick = {
                            viewModel.onEvent(NoteListEvent.DeleteNote(note))
                            scope.launch {
                                var result = scaffoldSate.snackbarHostState.showSnackbar(
                                    "Undo delete",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NoteListEvent.RestoreNote)
                                }
                            }
                        })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}