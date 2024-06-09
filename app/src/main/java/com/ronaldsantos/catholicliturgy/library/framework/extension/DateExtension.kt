package com.ronaldsantos.catholicliturgy.library.framework.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Date.asDateStringBrl: String
    get() {
        val formatter =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) //or use getDateInstance()
        return formatter.format(this)
    }
