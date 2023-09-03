package com.example.myhome.realm.model

import com.example.myhome.feature_home.domain.model.Camera
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class CameraRealm : RealmObject {
	@PrimaryKey
	var _id: ObjectId = ObjectId.invoke()
	var cameraId: Int = 0
	var name: String = ""
	var room: String = ""
	var snapshot: String = ""
	var isFavourite: Boolean = false
	var isRecording: Boolean = false

	companion object {
		fun toDomainCamera(cameraRealm: CameraRealm): Camera {
			return Camera(
				cameraId = cameraRealm.cameraId,
				name = cameraRealm.name,
				room = cameraRealm.room,
				snapshot = cameraRealm.snapshot,
				isFavourite = cameraRealm.isFavourite,
				isRecording = cameraRealm.isRecording
			)
		}
		fun toCameraRealm(camera: Camera): CameraRealm {
			val cameraRealm = CameraRealm()
			cameraRealm.cameraId = camera.cameraId
			cameraRealm.name = camera.name
			cameraRealm.room = camera.room ?: ""
			cameraRealm.snapshot = camera.snapshot
			cameraRealm.isFavourite = camera.isFavourite
			cameraRealm.isRecording = camera.isRecording
			return cameraRealm
		}
	}

}