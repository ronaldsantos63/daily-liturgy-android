package com.ronaldsantos.catholicliturgy.domain.model

import com.ronaldsantos.catholicliturgy.library.framework.extension.empty

data class DailyLiturgyDto(
    val entryTitle: String = String.empty,
    val liturgyDate: String = String.empty,
    val color: String = String.empty,
    val readings: List<ReadingsDto> = listOf(),
)
