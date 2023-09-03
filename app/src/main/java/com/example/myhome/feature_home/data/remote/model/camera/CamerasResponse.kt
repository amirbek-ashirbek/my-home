package com.example.myhome.feature_home.data.remote.model.camera


import com.example.myhome.realm.model.CameraRealm
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
        fun toCameras(camerasResponse: CamerasResponse): List<CameraRealm> {
            val cameraRealmList = mutableListOf<CameraRealm>()

            camerasResponse.camerasData.cameras.forEach { cameraResponse ->
                val cameraRealm = CameraRealm()
                cameraRealm.cameraId = cameraResponse.id
                cameraRealm.name = cameraResponse.name
                cameraRealm.room = cameraResponse.room ?: ""
                cameraRealm.snapshot = cameraResponse.snapshot
                cameraRealm.isFavourite = cameraResponse.favorites
                cameraRealm.isRecording = cameraResponse.rec
                cameraRealmList.add(cameraRealm)
            }

            return cameraRealmList
        }
    }
}