package com.ronaldsantos.catholicliturgy.data.model.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DailyLiturgyResponse(
    val color: String,
    val date: String,
    @SerializedName("date_string") val dateObject: DateObject,
    @SerializedName("entry_title") val entryTitle: String,
    val readings: Reading
)
