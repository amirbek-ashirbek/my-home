package com.example.myhome.feature_home.data.remote.model.camera


import com.example.myhome.feature_home.domain.model.Camera
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CamerasResponse(
    @SerialName("data")
    val camerasData: CamerasDataResponse,
    @SerialName("success")
    val success: Boolean
) {

    companion object {
        fun toCameras(camerasResponse: CamerasResponse): List<Camera> {
            val cameras = camerasResponse.camerasData.cameras

            return cameras.map { cameraResponse ->
                Camera(
                    cameraId = cameraResponse.id,
                    name = cameraResponse.name,
                    room = cameraResponse.room,
                    snapshot = cameraResponse.snapshot,
                    isFavourite = cameraResponse.favorites,
                    isRecording = cameraResponse.rec
                )
            }
        }
    }
}