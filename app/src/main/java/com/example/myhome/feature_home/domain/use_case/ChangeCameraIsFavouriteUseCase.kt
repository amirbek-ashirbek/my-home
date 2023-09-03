package com.example.myhome.feature_home.domain.use_case

import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.realm.model.CameraRealm
import javax.inject.Inject

class ChangeCameraIsFavouriteUseCase @Inject constructor(
	private val cameraRepository: CameraRepository
) {

	suspend fun execute(camera: CameraRealm) {
		cameraRepository.updateCameraIsFavourite(camera = camera)
	}

}