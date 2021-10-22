package com.pixel.cleannote.domain.util

sealed class SortBy(val orderByType: OrderByType) {
    class Title(orderByType: OrderByType) : SortBy(orderByType)
    class Date(orderByType: OrderByType) : SortBy(orderByType)
    class BackgroundColor(orderByType: OrderByType) : SortBy(orderByType)

    fun copy(orderByType: OrderByType): SortBy {
        return when (this) {
            is Title -> Title(orderByType = orderByType)
            is Date -> Title(orderByType = orderByType)
            is BackgroundColor -> Title(orderByType = orderByType)
        }
    }
}