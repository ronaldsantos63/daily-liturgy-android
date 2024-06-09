package com.ronaldsantos.catholicliturgy.presentation.home

import androidx.annotation.StringRes
import com.ronaldsantos.catholicliturgy.R
import com.ronaldsantos.catholicliturgy.domain.model.ReadingTypeDto

enum class HomeTabs(@StringRes val value: Int) {
    FirstReading(R.string.tab_daily_liturgy_first_reading),
    Psalm(R.string.tab_daily_liturgy_psalm_reading),
    SecondReading(R.string.tab_daily_liturgy_second_reading),
    Gospel(R.string.tab_daily_liturgy_gospel_reading);

    companion object {
        fun parseType(type: ReadingTypeDto): HomeTabs = when (type) {
            ReadingTypeDto.FirstReading -> FirstReading
            ReadingTypeDto.Psalm -> Psalm
            ReadingTypeDto.Gospel -> Gospel
            ReadingTypeDto.SecondaryReading -> SecondReading
        }
    }
}