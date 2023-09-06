package com.example.myhome.feature_home.data.remote.model.camera


import com.example.myhome.realm.model.Camera
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
            val cameraList = mutableListOf<Camera>()

            camerasResponse.camerasData.cameras.forEach { cameraResponse ->
                val camera = Camera()
                camera.cameraId = cameraResponse.id
                camera.name = cameraResponse.name
                camera.room = cameraResponse.room ?: ""
                camera.snapshot = cameraResponse.snapshot
                camera.isFavourite = cameraResponse.favorites
                camera.isRecording = cameraResponse.rec
                cameraList.add(camera)
            }

            return cameraList
        }
    }
}