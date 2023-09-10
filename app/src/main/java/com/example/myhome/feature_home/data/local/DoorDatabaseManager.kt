package com.example.myhome.feature_home.data.local

import com.example.myhome.realm.model.Door
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DoorDatabaseManager @Inject constructor(
	private val realm: Realm
) {

	fun getDoorsFromDatabase(): Flow<List<Door>> {
		return realm.query<Door>().asFlow().map { it.list }
	}

	suspend fun insertDoor(door: Door) {
		realm.write { copyToRealm(door) }
	}

	fun updateDoorIsFavourite(door: Door) {
		realm.writeBlocking {
			val queriedDoor = query<Door>(query = "_id == $0", door._id).first().find()
			queriedDoor?.isFavourite = !queriedDoor?.isFavourite!!
		}
	}

	fun updateDoorIsLocked(door: Door) {
		realm.writeBlocking {
			val queriedDoor = query<Door>(query = "_id == $0", door._id).first().find()
			queriedDoor?.isLocked = !queriedDoor?.isLocked!!
		}
	}

	fun updateDoorName(door: Door, doorName: String) {
		realm.writeBlocking {
			val queriedDoor = query<Door>(query = "_id == $0", door._id).first().find()
			if (queriedDoor?.name != doorName) {
				queriedDoor?.name = doorName
			}
		}
	}

	suspend fun updateDoors() {
		val allDoors: RealmResults<Door> = realm.query<Door>().find()
		val possibleNames = listOf("Подъезд 1", "Выход на пожарную лестницу", "Подъезд 2")
		var index = 0

		allDoors.forEach { door ->
			realm.write {
				val latestDoor = findLatest(door)
				latestDoor?.isFromDatabase = true

				if (latestDoor?.snapshot != null) {
					latestDoor.name = "Домофон"
				} else {
					latestDoor?.name = possibleNames[index]
					index++
				}
			}
		}
	}

}