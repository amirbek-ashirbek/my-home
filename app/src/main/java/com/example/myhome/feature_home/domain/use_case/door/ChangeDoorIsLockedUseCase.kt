package com.example.myhome.feature_home.domain.use_case.door

import com.example.myhome.feature_home.domain.repository.DoorRepository
import com.example.myhome.realm.model.Door
import javax.inject.Inject

class ChangeDoorIsLockedUseCase @Inject constructor(
	private val doorRepository: DoorRepository
) {

	suspend fun execute(door: Door) {
		doorRepository.updateDoorIsLocked(door = door)
	}

}