package com.example.myhome.feature_home.domain.use_case.camera

import com.example.myhome.feature_home.domain.repository.CameraRepository
import javax.inject.Inject

class UpdateCamerasUseCase @Inject constructor(
	private val cameraRepository: CameraRepository
) {

	suspend fun execute() {
		cameraRepository.updateCameras()
	}

}