package com.example.myhome.feature_home.domain.use_case.camera

import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.realm.model.Camera
import javax.inject.Inject

class ChangeCameraIsFavouriteUseCase @Inject constructor(
	private val cameraRepository: CameraRepository
) {

	suspend fun execute(camera: Camera) {
		cameraRepository.updateCameraIsFavourite(camera = camera)
	}

}