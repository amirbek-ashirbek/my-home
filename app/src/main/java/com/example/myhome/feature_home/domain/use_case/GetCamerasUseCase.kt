package com.example.myhome.feature_home.domain.use_case

import android.util.Log
import com.example.myhome.feature_home.domain.model.Camera
import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.realm.model.CameraRealm
import com.example.myhome.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCamerasUseCase @Inject constructor(
	private val cameraRepository: CameraRepository
) {

	suspend fun execute(): Flow<List<Camera>> {

		val camerasFromDatabase = cameraRepository.getCamerasFromDatabase().firstOrNull()
		Log.d("camerasFromDatabase!!!", "$camerasFromDatabase")

		return if (camerasFromDatabase.isNullOrEmpty()) {
			cameraRepository.getCamerasFromNetwork().map { response ->
				when (response) {
					is Response.Success -> {
						response.data?.let { cameras ->
							cameras.forEach {  camera ->
								cameraRepository.insertCamera(CameraRealm.toCameraRealm(camera))
								Log.d("GetCamerasTestUseCase", "yooo")
							}
						}
						response.data ?: emptyList()
					}
					is Response.Error -> emptyList()
				}
			}
		} else {
			val cameras = camerasFromDatabase.map { cameraRealm ->
				CameraRealm.toDomainCamera(cameraRealm)
			}
			flowOf(cameras)
		}
	}

}