package com.example.myhome.feature_home.data.local

import com.example.myhome.realm.model.Camera
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CameraDatabaseManager @Inject constructor(
	private val realm: Realm
) {

	fun getCamerasFromDatabase(): Flow<List<Camera>> {
		return realm.query<Camera>().asFlow().map { it.list }
	}

	suspend fun insertCamera(camera: Camera) {
		realm.write { copyToRealm(camera) }
	}

	fun updateCameraIsFavourite(camera: Camera) {
		realm.writeBlocking {
			val queriedCamera = query<Camera>(query = "_id == $0", camera._id).first().find()
			queriedCamera?.isFavourite = !queriedCamera?.isFavourite!!
		}
	}

	suspend fun updateCameras() {

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