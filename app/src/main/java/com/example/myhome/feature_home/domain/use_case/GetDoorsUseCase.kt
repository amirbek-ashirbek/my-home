package com.example.myhome.feature_home.domain.use_case

import com.example.myhome.feature_home.domain.repository.DoorRepository
import com.example.myhome.realm.model.Door
import com.example.myhome.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDoorsUseCase @Inject constructor(
	private val doorRepository: DoorRepository
) {

	suspend fun execute(): Flow<List<Door>> {
		val doorsFromDatabase = getDoorsFromDatabase()

		if (doorsFromDatabase.isNotEmpty()) {
			return flowOf(doorsFromDatabase)
		} else {
			val doorsFromNetwork = getDoorsFromNetwork().first()
			insertDoorsIntoDatabase(doors = doorsFromNetwork)
			updateDoorsInDatabase()
			return flowOf(getDoorsFromDatabase())
		}
	}

	private suspend fun getDoorsFromDatabase(): List<Door> {
		return doorRepository.getDoorsFromDatabase().firstOrNull() ?: emptyList()
	}

	private fun getDoorsFromNetwork(): Flow<List<Door>> {
		return doorRepository.getDoorsFromNetwork()
			.map { response ->
				when (response) {
					is Response.Success -> response.data ?: emptyList()
					is Response.Error -> emptyList()
				}
			}
	}

	private suspend fun insertDoorsIntoDatabase(doors: List<Door>) {
		doors.forEach { door ->
			doorRepository.insertDoor(door = door)
		}
	}

	private suspend fun updateDoorsInDatabase() {
		doorRepository.updateDoors()
	}

}