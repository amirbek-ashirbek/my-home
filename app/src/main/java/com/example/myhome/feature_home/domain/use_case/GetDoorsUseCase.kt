package com.example.myhome.feature_home.domain.use_case

import com.example.myhome.feature_home.domain.repository.DoorRepository
import com.example.myhome.realm.model.Door
import com.example.myhome.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDoorsUseCase @Inject constructor(
	private val doorRepository: DoorRepository
) {

	suspend fun execute(): Flow<List<Door>> {
		val doorsFromDatabase = getDoorsFromDatabase()

		return if (doorsFromDatabase.isNotEmpty()) {
			flowOf(doorsFromDatabase)
		} else {
			getDoorsFromNetwork()
		}
	}

	private suspend fun getDoorsFromDatabase(): List<Door> {
		return doorRepository.getDoorsFromDatabase().firstOrNull() ?: emptyList()
	}

	private suspend fun getDoorsFromNetwork(): Flow<List<Door>> {
		return doorRepository.getDoorsFromNetwork()
			.map { response ->
				when (response) {
					is Response.Success -> {
						response.data?.let { doors ->
							insertDoorsIntoDatabase(doors)
						}
						updateDoorsInDatabase()
						response.data ?: emptyList()
					}
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