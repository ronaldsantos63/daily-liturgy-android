package com.ronaldsantos.catholicliturgy.data.local.converter

import androidx.room.TypeConverter
import com.ronaldsantos.catholicliturgy.data.model.local.ReadingsEntity
import com.ronaldsantos.catholicliturgy.library.framework.extension.fromJsonList
import com.ronaldsantos.catholicliturgy.library.framework.extension.toJson

class ReadingsEntityListConverter {
    @TypeConverter
    fun toStringJson(readingsList: List<ReadingsEntity>?): String {
        return readingsList.toJson()
    }

    @TypeConverter
    fun fromStringJson(json: String): List<ReadingsEntity>? {
        return json.fromJsonList()
    }
}
