package com.example.myhome.feature_home.data.repository

import com.example.myhome.feature_home.data.local.CameraDatabaseManager
import com.example.myhome.feature_home.data.remote.HomeApi
import com.example.myhome.feature_home.data.remote.model.camera.CamerasResponse
import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.realm.model.Camera
import com.example.myhome.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
	private val homeApi: HomeApi,
	private val cameraDatabaseManager: CameraDatabaseManager
) : CameraRepository {

	override fun getCamerasFromNetwork(): Flow<Response<List<Camera>>> = flow {
		try {
			val camerasResponse = homeApi.getCameras()
			val cameras = CamerasResponse.toCameras(camerasResponse)
			emit(Response.Success(cameras))
		} catch (e: Exception) {
			emit(Response.Error(e.message))
		}
	}.flowOn(Dispatchers.IO)

	override fun getCamerasFromDatabase(): Flow<List<Camera>> {
		return cameraDatabaseManager.getCamerasFromDatabase()
	}

	override suspend fun insertCamera(camera: Camera) {
		cameraDatabaseManager.insertCamera(camera = camera)
	}

	override suspend fun updateCameraIsFavourite(camera: Camera) {
		cameraDatabaseManager.updateCameraIsFavourite(camera = camera)
	}

	override suspend fun updateCameras() {
		cameraDatabaseManager.updateCameras()
	}

}