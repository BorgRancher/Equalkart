package tech.borgranch.equalkart.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("title")
    val name: String,
    val price: Double,
)
