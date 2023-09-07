package com.example.myhome.feature_home.data.remote.model.door


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DoorsData(
    @SerialName("favorites")
    val favorites: Boolean,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("room")
    val room: String?,
    @SerialName("snapshot")
    val snapshot: String? = null
)