package com.example.myhome.feature_home.data.repository

import com.example.myhome.feature_home.data.remote.HomeApi
import com.example.myhome.feature_home.data.remote.model.door.DoorsResponse
import com.example.myhome.feature_home.domain.repository.DoorRepository
import com.example.myhome.realm.model.Door
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
import kotlin.random.Random

class DoorRepositoryImpl @Inject constructor(
	private val homeApi: HomeApi,
	private val realm: Realm
) : DoorRepository {

	override fun getDoorsFromNetwork(): Flow<Response<List<Door>>> = flow {
		try {
			val doorsResponse = homeApi.getDoors()
			val doors = DoorsResponse.toDoors(doorsResponse)
			emit(Response.Success(doors))
		} catch (e: Exception) {
			emit(Response.Error(e.message))
		}
	}.flowOn(Dispatchers.IO)

	override fun getDoorsFromDatabase(): Flow<List<Door>> {
		return realm.query<Door>().asFlow().map { it.list }
	}

	override suspend fun insertDoor(door: Door) {
		realm.write { copyToRealm(door) }
	}

	override suspend fun updateDoorIsFavourite(door: Door) {
		realm.writeBlocking {
			val queriedDoor = query<Door>(query = "_id == $0", door._id).first().find()
			queriedDoor?.isFavourite = !queriedDoor?.isFavourite!!
		}
	}

	override suspend fun updateDoorIsLocked(door: Door) {
		realm.writeBlocking {
			val queriedDoor = query<Door>(query = "_id == $0", door._id).first().find()
			queriedDoor?.isLocked = !queriedDoor?.isLocked!!
		}
	}

	override suspend fun updateDoors() {
		val allDoors: RealmResults<Door> = realm.query<Door>().find()
		val possibleNames = listOf("Подъезд 1", "Выход на пожарную лестницу", "Подъезд 2")

		allDoors.forEach { door ->
			realm.write {
				val latestDoor = findLatest(door)
				latestDoor?.isFromDatabase = true

				if (latestDoor?.snapshot != null) {
					latestDoor.name = "Домофон"
				} else {
					val randomIndex = Random.nextInt(possibleNames.size)
					latestDoor?.name = possibleNames[randomIndex]
				}
			}
		}
	}

}