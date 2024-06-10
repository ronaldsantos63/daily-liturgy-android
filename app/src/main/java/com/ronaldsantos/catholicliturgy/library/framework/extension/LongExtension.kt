package com.ronaldsantos.catholicliturgy.library.framework.extension

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.formatWithSkeleton

@OptIn(ExperimentalMaterial3Api::class)
val Long.asDateStringBR: String
    get() {
        return formatWithSkeleton(
            this,
            "dd/MM/yyyy",
            CalendarLocale.forLanguageTag("pt-BR"),
            mutableMapOf()
        )
    }
