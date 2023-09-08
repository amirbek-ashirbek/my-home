package com.example.myhome.feature_home.domain.use_case

import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.realm.model.Camera
import com.example.myhome.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCamerasUseCase @Inject constructor(
	private val cameraRepository: CameraRepository
) {

	suspend fun execute(): Flow<List<Camera>> {
		val camerasFromDatabase = getCamerasFromDatabase()

		if (camerasFromDatabase.isNotEmpty()) {
			return flowOf(camerasFromDatabase)
		} else {
			val camerasFromNetwork = getCamerasFromNetwork().first()
			insertCamerasIntoDatabase(cameras = camerasFromNetwork)
			updateCamerasInDatabase()
			return flowOf(getCamerasFromDatabase())
		}
	}

	private suspend fun getCamerasFromDatabase(): List<Camera> {
		return cameraRepository.getCamerasFromDatabase().firstOrNull() ?: emptyList()
	}

	private fun getCamerasFromNetwork(): Flow<List<Camera>> {
		return cameraRepository.getCamerasFromNetwork()
			.map { response ->
				when (response) {
					is Response.Success -> response.data ?: emptyList()
					is Response.Error -> emptyList()
				}
			}
	}

	private suspend fun insertCamerasIntoDatabase(cameras: List<Camera>) {
		cameras.forEach { camera ->
			cameraRepository.insertCamera(camera)
		}
	}

	private suspend fun updateCamerasInDatabase() {
		cameraRepository.updateCameras()
	}

}