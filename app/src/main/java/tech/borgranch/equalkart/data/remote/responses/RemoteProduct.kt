package tech.borgranch.equalkart.data.remote.responses

import com.google.gson.annotations.SerializedName

data class RemoteProduct(
    @SerializedName("title")
    val name: String,
    val price: Double = 0.0,
)
