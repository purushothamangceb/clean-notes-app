package com.pixel.cleannote.presentation.edit_note.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pixel.cleannote.domain.model.Note
import com.pixel.cleannote.presentation.edit_note.EditNoteViewModel
import com.pixel.cleannote.presentation.edit_note.NoteEditEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EditNotePage(
    navController: NavController,
    backgroundColor: Int,
    viewModel: EditNoteViewModel = hiltViewModel()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val backgroundColorAnimatable = remember {
        Animatable(
            initialValue = Color(if (backgroundColor != -1) backgroundColor else viewModel.backgroundColor.value)
        )
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is EditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(NoteEditEvent.SaveNote) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save Note")
            }
        }, scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColorAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.backgroundColors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(color))
                            .border(
                                3.dp,
                                shape = RoundedCornerShape(12.dp),
                                color = if (viewModel.backgroundColor.value == color) Color.Black else Color.Transparent
                            )
                            .clickable {
                                scope.launch {
                                    backgroundColorAnimatable.animateTo(
                                        targetValue = Color(color),
                                        animationSpec = tween(durationMillis = 500)
                                    )
                                }
                                viewModel.onEvent(NoteEditEvent.ChangeBackgroundColor(color))
                            }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                TransparentHintText(
                    text = titleState.text,
                    hint = titleState.hint,
                    onValueChange = {
                        viewModel.onEvent(NoteEditEvent.TitleEnteredEvent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(NoteEditEvent.ChangeTitleFocus(it))
                    },
                    textStyle = MaterialTheme.typography.h5,
                    isVisibleHint = titleState.isHintVisible,
                    isSingleLine = true
                )
                Spacer(modifier = Modifier.width(24.dp))
                TransparentHintText(
                    text = contentState.text,
                    hint = contentState.hint,
                    onValueChange = {
                        viewModel.onEvent(NoteEditEvent.ContentEnteredEvent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(NoteEditEvent.ChangeContentFocus(it))
                    },
                    textStyle = MaterialTheme.typography.body1,
                    isVisibleHint = contentState.isHintVisible,
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}