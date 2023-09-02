package com.example.myhome.feature_home.data.remote.model.camera


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CameraResponse(
    @SerialName("favorites")
    val favorites: Boolean,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("rec")
    val rec: Boolean,
    @SerialName("room")
    val room: String?,
    @SerialName("snapshot")
    val snapshot: String
)