package com.example.myhome.feature_home.data.repository

import com.example.myhome.feature_home.data.remote.HomeApi
import com.example.myhome.feature_home.data.remote.model.camera.CamerasResponse
import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.realm.model.Camera
import com.example.myhome.util.Response
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
	private val homeApi: HomeApi,
	private val realm: Realm
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
		return realm.query<Camera>().asFlow().map { it.list }
	}

	override suspend fun insertCamera(camera: Camera) {
		realm.write { copyToRealm(camera) }
	}

	override suspend fun updateCameraIsFavourite(camera: Camera) {
		realm.writeBlocking {
			val queriedCamera = query<Camera>(query = "_id == $0", camera._id).first().find()
			queriedCamera?.isFavourite = !queriedCamera?.isFavourite!!
		}
	}

	override suspend fun updateCameras() {

		val allCameras: RealmResults<Camera> =
			realm.query<Camera>().find()
		for (camera in allCameras) {
			realm.write {
				val latestCamera = findLatest(camera)
				latestCamera?.isFromDatabase = true
				latestCamera?.name = latestCamera?.name?.replace("Camera","Камера") ?: ""
			}
		}

		val camerasInLivingRoom: RealmResults<Camera> =
			realm.query<Camera>("room == $0", "FIRST").find()
		for (camera in camerasInLivingRoom) {
			realm.write { findLatest(camera)?.room = "Гостиная" }
		}

	}

}