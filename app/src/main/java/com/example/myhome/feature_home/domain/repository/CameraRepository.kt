package com.example.myhome.feature_home.domain.repository

import com.example.myhome.feature_home.domain.model.Camera
import com.example.myhome.realm.model.CameraRealm
import com.example.myhome.util.Response
import kotlinx.coroutines.flow.Flow

interface CameraRepository {

	suspend fun getCamerasFromNetwork(): Flow<Response<List<Camera>>>

	suspend fun getCamerasFromDatabase(): Flow<List<CameraRealm>>

	suspend fun insertCamera(camera: CameraRealm)

}