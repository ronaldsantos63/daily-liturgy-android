package com.ronaldsantos.catholicliturgy.domain.model

data class ReadingsDto(
    val firstReading: String,
    val psalm: String,
    val gospel: String,
    val secondReading: String? = null
)
