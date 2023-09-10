package com.example.myhome.feature_home.domain.use_case.door

import com.example.myhome.feature_home.domain.repository.DoorRepository
import com.example.myhome.realm.model.Door
import javax.inject.Inject

class EditDoorNameUseCase @Inject constructor(
	private val doorRepository: DoorRepository
) {

	suspend fun execute(door: Door, doorName: String) {
		doorRepository.updateDoorName(
			door = door,
			doorName = doorName
		)
	}

}