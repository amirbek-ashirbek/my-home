package com.example.myhome.feature_home.data.remote.model.camera


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CamerasDataResponse(
    @SerialName("cameras")
    val cameras: List<CameraResponse>,
    @SerialName("room")
    val room: List<String>
)