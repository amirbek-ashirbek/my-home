package com.example.myhome.feature_home.presentation.my_home_screen

import com.example.myhome.realm.model.Camera

sealed class MyHomeEvent {
	data class CameraIsFavouriteToggled(val camera: Camera) : MyHomeEvent()
	object CamerasPullRefreshed : MyHomeEvent()
}