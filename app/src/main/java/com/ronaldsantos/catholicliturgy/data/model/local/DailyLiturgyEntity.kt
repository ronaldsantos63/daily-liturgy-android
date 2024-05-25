package com.ronaldsantos.catholicliturgy.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DailyLiturgyEntity.TABLE_NAME)
data class DailyLiturgyEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID) val id: Int,
    @ColumnInfo(name = COLUMN_ENTRY_TITLE) val entryTitle: String,
    @ColumnInfo(name = COLUMN_LITURGY_DATE) val liturgyDate: String,
    @ColumnInfo(name = COLUMN_COLOR) val color: String,
    @Embedded(prefix = PREF_READING) val readings: ReadingsEntity,
    @ColumnInfo(name = COLUMN_CREATED) val created: String,
) {
    companion object {
        const val TABLE_NAME = "daily_liturgy"
        const val COLUMN_ID = "id"
        const val COLUMN_ENTRY_TITLE = "entry_title"
        const val COLUMN_LITURGY_DATE = "liturgy_date"
        const val COLUMN_CREATED = "created"
        const val COLUMN_COLOR = "color"
        const val PREF_READING = "reading_"
    }
}
