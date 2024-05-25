package com.ronaldsantos.catholicliturgy.data.model.local

data class ReadingsEntity(
    val firstReading: String,
    val psalm: String,
    val gospel: String,
    val secondReading: String? = null
)
