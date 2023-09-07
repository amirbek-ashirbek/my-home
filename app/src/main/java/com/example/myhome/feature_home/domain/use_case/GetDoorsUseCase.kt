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

		val doorsFromDatabase = doorRepository.getDoorsFromDatabase().firstOrNull()

		return if (!doorsFromDatabase.isNullOrEmpty()) {
			flowOf(doorsFromDatabase)
		} else {
			doorRepository.getDoorsFromNetwork().map { response ->
				when (response) {
					is Response.Success -> {
						response.data?.let { doors ->
							doors.forEach {  door ->
								doorRepository.insertDoor(door = door)
							}
						}
						response.data ?: emptyList()
					}
					is Response.Error -> {
						emptyList()
					}
				}
			}
		}

	}
}