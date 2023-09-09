package com.example.myhome.feature_home.domain.use_case.door

import com.example.myhome.feature_home.domain.repository.DoorRepository
import com.example.myhome.realm.model.Door
import javax.inject.Inject

class SaveDoorsToDatabaseUseCase @Inject constructor(
	private val doorRepository: DoorRepository
) {

	suspend fun execute(doors: List<Door>) {
		doors.forEach { door ->
			doorRepository.insertDoor(door)
		}
	}

}