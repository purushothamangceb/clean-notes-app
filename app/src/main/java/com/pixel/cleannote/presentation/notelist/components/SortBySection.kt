package com.pixel.cleannote.presentation.notelist.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pixel.cleannote.domain.util.OrderByType
import com.pixel.cleannote.domain.util.SortBy

@Composable
fun SortBySection(modifier: Modifier, sortBy: SortBy, onSortBy: (SortBy) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row() {
            DefaultRadioButton(
                text = "Title",
                selected = sortBy is SortBy.Title,
                onSelect = { onSortBy(SortBy.Title(sortBy.orderByType)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = sortBy is SortBy.Date,
                onSelect = { onSortBy(SortBy.Date(sortBy.orderByType)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Background Color",
                selected = sortBy is SortBy.BackgroundColor,
                onSelect = { onSortBy(SortBy.BackgroundColor(sortBy.orderByType)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = "Ascending",
                selected = sortBy.orderByType is OrderByType.Ascending,
                onSelect = { onSortBy(sortBy.copy(OrderByType.Ascending)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = sortBy.orderByType is OrderByType.Descending,
                onSelect = { onSortBy(sortBy.copy(OrderByType.Descending)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}