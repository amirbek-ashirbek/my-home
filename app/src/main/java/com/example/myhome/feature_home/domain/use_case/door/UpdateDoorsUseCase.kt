package com.example.myhome.feature_home.domain.use_case.door

import com.example.myhome.feature_home.domain.repository.DoorRepository
import javax.inject.Inject

class UpdateDoorsUseCase @Inject constructor(
	private val doorRepository: DoorRepository
) {

	suspend fun execute() {
		doorRepository.updateDoors()
	}

}