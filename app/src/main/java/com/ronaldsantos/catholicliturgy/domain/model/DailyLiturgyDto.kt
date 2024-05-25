package com.ronaldsantos.catholicliturgy.domain.model

data class DailyLiturgyDto(
    val id: Int,
    val entryTitle: String,
    val liturgyDate: String,
    val color: String,
    val readings: ReadingsDto,
    val created: String,
)
