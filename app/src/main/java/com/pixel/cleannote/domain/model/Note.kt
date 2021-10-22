package com.pixel.cleannote.domain.model

import androidx.compose.ui.graphics.toArgb
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pixel.cleannote.ui.theme.*

/**
 * Data class to represent a single note
 *
 * @param title the title of the Note
 * @param content the content of the Note
 * @param createdOn the created datetime
 * @param modifiedOn the last modified datetime
 * @param backgroundColor the background color of the note
 */
@Entity
data class Note(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val content: String,
    val createdOn: Long,
    val modifiedOn: Long,
    val backgroundColor: Int
) {
    companion object {
        val backgroundColors = listOf(Pink.toArgb(), Teal.toArgb(), Blue.toArgb(), Cyan.toArgb())
    }
}

class InvalidNoteException(message: String) : Exception(message)