package com.ronaldsantos.catholicliturgy.data.model.remote.response

import com.google.gson.annotations.SerializedName

data class Reading(
    @SerializedName("first_reading") val firstReading: String,
    @SerializedName("psalm") val psalm: String,
    @SerializedName("second_reading") val secondReading: String,
    @SerializedName("gospel") val gospel: String,
)
