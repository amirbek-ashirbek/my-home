package com.example.myhome.feature_home.domain.repository

import com.example.myhome.realm.model.CameraRealm
import com.example.myhome.util.Response
import kotlinx.coroutines.flow.Flow

interface CameraRepository {

	fun getCamerasFromNetwork(): Flow<Response<List<CameraRealm>>>

	fun getCamerasFromDatabase(): Flow<List<CameraRealm>>

	suspend fun insertCamera(camera: CameraRealm)

	suspend fun updateCameraIsFavourite(camera: CameraRealm)

}