package com.ronaldsantos.catholicliturgy.data.model.local

data class ReadingsEntity(
    val type: ReadingTypeEntity,
    val text: String,
) {
    constructor(): this(ReadingTypeEntity.FirstReading, "")
}
