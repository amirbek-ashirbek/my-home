package com.example.myhome.feature_home.data.repository

import com.example.myhome.feature_home.data.remote.HomeApi
import com.example.myhome.feature_home.data.remote.model.door.DoorsResponse
import com.example.myhome.feature_home.domain.repository.DoorRepository
import com.example.myhome.realm.model.Door
import com.example.myhome.util.Response
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

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

}