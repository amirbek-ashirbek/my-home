package com.example.myhome.feature_home.domain.use_case

import com.example.myhome.feature_home.domain.model.Camera
import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.util.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCamerasUseCase @Inject constructor(
	private val cameraRepository: CameraRepository
) {

	suspend fun execute(): Flow<Response<List<Camera>>> {
		return cameraRepository.getCamerasFromNetwork()
	}

}