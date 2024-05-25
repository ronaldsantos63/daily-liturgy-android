package com.ronaldsantos.catholicliturgy.data.local.converter

import androidx.room.TypeConverter
import com.ronaldsantos.catholicliturgy.library.framework.extension.fromJson
import com.ronaldsantos.catholicliturgy.library.framework.extension.toJson

class StringListConverter {
    @TypeConverter
    fun toListOfStrings(stringValue: String): List<String>? {
        return stringValue.fromJson()
    }

    @TypeConverter
    fun fromListOfStrings(listOfString: List<String>?): String {
        return listOfString.toJson()
    }
}
