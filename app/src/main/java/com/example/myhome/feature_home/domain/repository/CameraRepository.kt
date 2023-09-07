package com.example.myhome.feature_home.domain.repository

import com.example.myhome.realm.model.Camera
import com.example.myhome.util.Response
import kotlinx.coroutines.flow.Flow

interface CameraRepository {

	fun getCamerasFromNetwork(): Flow<Response<List<Camera>>>

	fun getCamerasFromDatabase(): Flow<List<Camera>>

	suspend fun insertCamera(camera: Camera)

	suspend fun updateCameraIsFavourite(camera: Camera)

	suspend fun updateCameras()

}