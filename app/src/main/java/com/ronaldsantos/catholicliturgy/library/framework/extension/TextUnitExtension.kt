package com.ronaldsantos.catholicliturgy.library.framework.extension

import androidx.compose.ui.unit.TextUnit

operator fun TextUnit.plus(other: TextUnit): TextUnit {
    return TextUnit(value + other.value, type)
}

operator fun TextUnit.minus(other: TextUnit): TextUnit {
    return TextUnit(value - other.value, type)
}

@Suppress("UNUSED")
fun TextUnit.coerceAtLeast(min: TextUnit): TextUnit {
    return if (value < min.value) min else this
}