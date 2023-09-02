package com.example.myhome.realm.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class DoorRealm : RealmObject {
	@PrimaryKey
	var _id: ObjectId = ObjectId.invoke()
	var doorId: Int = 0
	var name: String = ""
	var room: String = ""
	var snapshot: String? = null
	var isFavourite: Boolean = false

}