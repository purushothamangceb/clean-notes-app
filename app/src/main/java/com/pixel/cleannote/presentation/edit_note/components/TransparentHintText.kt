package com.pixel.cleannote.presentation.edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun TransparentHintText(
    text: String,
    hint: String,
    isVisibleHint: Boolean = true,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    isSingleLine: Boolean = true,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = isSingleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) }
        )
        if (isVisibleHint) {
            Text(text = hint, style = textStyle, color = Color.DarkGray)
        }
    }
}