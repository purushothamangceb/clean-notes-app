package com.pixel.cleannote.domain.util

sealed class OrderByType {
    object Ascending : OrderByType()
    object Descending : OrderByType()
}