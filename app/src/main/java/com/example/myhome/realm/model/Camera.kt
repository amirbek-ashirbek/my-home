package com.example.myhome.realm.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Camera : RealmObject {
	@PrimaryKey
	var _id: ObjectId = ObjectId.invoke()
	var cameraId: Int = 0
	var name: String = ""
	var room: String = ""
	var snapshot: String = ""
	var isFavourite: Boolean = false
	var isRecording: Boolean = false
	var isFromDatabase: Boolean = true
}