package com.example.myhome.feature_home.data.remote.model.door


import com.example.myhome.realm.model.Door
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DoorsResponse(
	@SerialName("data")
    val `data`: List<DoorsData>,
	@SerialName("success")
    val success: Boolean
) {

	companion object {
		fun toDoors(doorsResponse: DoorsResponse): List<Door> {
			val doorsList = mutableListOf<Door>()

			doorsResponse.data.forEach { doorResponse ->
				val door = Door()
				door.doorId = doorResponse.id
				door.name = doorResponse.name
				door.room = doorResponse.room
				door.snapshot = doorResponse.snapshot
				door.isFavourite = doorResponse.favorites
				door.isFromDatabase = false
				doorsList.add(door)
			}
			return doorsList
		}
	}

}
