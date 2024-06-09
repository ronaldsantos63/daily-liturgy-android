package com.ronaldsantos.catholicliturgy.library.framework.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Long.asDateString: String
    get() {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(this))
    }
