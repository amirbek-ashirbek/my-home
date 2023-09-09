package com.example.myhome.feature_home.domain.use_case

import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.realm.model.Camera
import javax.inject.Inject

class SaveCamerasToDatabaseUseCase @Inject constructor(
	private val cameraRepository: CameraRepository
) {

	suspend fun execute(cameras: List<Camera>) {
		cameras.forEach { camera ->
			cameraRepository.insertCamera(camera)
		}
	}

}