package com.example.myhome.feature_home.data.repository

import com.example.myhome.feature_home.data.local.DoorDatabaseManager
import com.example.myhome.feature_home.data.remote.HomeApi
import com.example.myhome.feature_home.data.remote.model.door.DoorsResponse
import com.example.myhome.feature_home.domain.repository.DoorRepository
import com.example.myhome.realm.model.Door
import com.example.myhome.util.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DoorRepositoryImpl @Inject constructor(
	private val homeApi: HomeApi,
	private val doorDatabaseManager: DoorDatabaseManager
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
		return doorDatabaseManager.getDoorsFromDatabase()
	}

	override suspend fun insertDoor(door: Door) {
		doorDatabaseManager.insertDoor(door = door)
	}

	override suspend fun updateDoorIsFavourite(door: Door) {
		doorDatabaseManager.updateDoorIsFavourite(door = door)
	}

	override suspend fun updateDoorIsLocked(door: Door) {
		doorDatabaseManager.updateDoorIsLocked(door = door)
	}

	override suspend fun updateDoors() {
		doorDatabaseManager.updateDoors()
	}

}