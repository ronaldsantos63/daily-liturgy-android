package com.ronaldsantos.catholicliturgy.domain.extension

import com.ronaldsantos.catholicliturgy.data.model.local.DailyLiturgyEntity
import com.ronaldsantos.catholicliturgy.data.model.local.ReadingTypeEntity
import com.ronaldsantos.catholicliturgy.data.model.local.ReadingsEntity
import com.ronaldsantos.catholicliturgy.data.model.remote.response.DailyLiturgyResponse
import com.ronaldsantos.catholicliturgy.data.model.remote.response.Reading
import com.ronaldsantos.catholicliturgy.domain.model.DailyLiturgyDto
import com.ronaldsantos.catholicliturgy.domain.model.ReadingTypeDto
import com.ronaldsantos.catholicliturgy.domain.model.ReadingsDto
import com.ronaldsantos.catholicliturgy.library.framework.extension.asDateStringBrl
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val Reading.asDto: List<ReadingsDto>
    get() {
        val list = mutableListOf<ReadingsDto>().apply {
            add(
                ReadingsDto(
                    type = ReadingTypeDto.FirstReading,
                    text = firstReading,
                )
            )
            add(
                ReadingsDto(
                    type = ReadingTypeDto.Psalm,
                    text = psalm,
                )
            )
            if (secondReading != null) {
                add(
                    ReadingsDto(
                        type = ReadingTypeDto.SecondaryReading,
                        text = secondReading,
                    )
                )
            }
            add(
                ReadingsDto(
                    type = ReadingTypeDto.Gospel,
                    text = gospel,
                )
            )
        }
        return list
    }

val DailyLiturgyResponse.asDto: DailyLiturgyDto
    get() = DailyLiturgyDto(
        entryTitle = entryTitle,
        liturgyDate = date,
        color = color,
        readings = readings.asDto,
    )

val ReadingsDto.asEntity: ReadingsEntity
    get()  {
        return when (type) {
            ReadingTypeDto.FirstReading -> ReadingsEntity(
                type = ReadingTypeEntity.FirstReading,
                text = text,
            )
            ReadingTypeDto.Psalm -> ReadingsEntity(
                type = ReadingTypeEntity.Psalm,
                text = text,
            )
            ReadingTypeDto.Gospel -> ReadingsEntity(
                type = ReadingTypeEntity.Gospel,
                text = text,
            )
            ReadingTypeDto.SecondaryReading -> ReadingsEntity(
                type = ReadingTypeEntity.SecondaryReading,
                text = text,
            )
        }
    }

val DailyLiturgyDto.asEntity: DailyLiturgyEntity
    get() {
        return DailyLiturgyEntity(
            entryTitle = entryTitle,
            liturgyDate = liturgyDate,
            color = color,
            readings = readings.map { it.asEntity },
            created = Calendar.getInstance().time.asDateStringBrl
        )
    }

val ReadingsEntity.asDto: ReadingsDto
    get() = when(type){
        ReadingTypeEntity.FirstReading -> ReadingsDto(
            type = ReadingTypeDto.FirstReading,
            text = text,
        )
        ReadingTypeEntity.Psalm -> ReadingsDto(
            type = ReadingTypeDto.Psalm,
            text = text,
        )
        ReadingTypeEntity.Gospel -> ReadingsDto(
            type = ReadingTypeDto.Gospel,
            text = text,
        )
        ReadingTypeEntity.SecondaryReading -> ReadingsDto(
            type = ReadingTypeDto.SecondaryReading,
            text = text,
        )
    }

val DailyLiturgyEntity.asDto: DailyLiturgyDto
    get() = DailyLiturgyDto(
        entryTitle = entryTitle,
        liturgyDate = liturgyDate,
        color = color,
        readings = readings.map { it.asDto },
    )
