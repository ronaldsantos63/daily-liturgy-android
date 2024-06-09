package com.ronaldsantos.catholicliturgy.provider.resource

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes id: Int): String
}
