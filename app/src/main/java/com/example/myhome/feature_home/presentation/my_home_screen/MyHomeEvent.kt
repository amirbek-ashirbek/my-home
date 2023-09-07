package com.example.myhome.feature_home.presentation.my_home_screen

import com.example.myhome.realm.model.Camera
import com.example.myhome.realm.model.Door

sealed class MyHomeEvent {
	data class CameraIsFavouriteToggled(val camera: Camera) : MyHomeEvent()
	object CamerasPullRefreshed : MyHomeEvent()
	data class DoorIsFavouriteToggled(val door: Door) : MyHomeEvent()
	data class DoorLockClicked(val door: Door) : MyHomeEvent()
	data class DoorIsLockedToggled(val door: Door) : MyHomeEvent()
	object DoorsPullRefreshed : MyHomeEvent()
	object DoorLockDialogDismissed : MyHomeEvent()
}