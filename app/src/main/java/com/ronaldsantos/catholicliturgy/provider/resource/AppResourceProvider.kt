package com.ronaldsantos.catholicliturgy.provider.resource

import android.content.Context

class AppResourceProvider(
    private val context: Context
) : ResourceProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }
}
