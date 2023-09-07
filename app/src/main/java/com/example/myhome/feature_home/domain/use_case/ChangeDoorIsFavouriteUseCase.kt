package com.example.myhome.feature_home.domain.use_case

import com.example.myhome.feature_home.domain.repository.DoorRepository
import com.example.myhome.realm.model.Door
import javax.inject.Inject

class ChangeDoorIsFavouriteUseCase @Inject constructor(
	private val doorRepository: DoorRepository
) {

	suspend fun execute(door: Door) {
		doorRepository.updateDoorIsFavourite(door = door)
	}

}