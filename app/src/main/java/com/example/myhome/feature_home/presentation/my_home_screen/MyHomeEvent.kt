package com.example.myhome.feature_home.presentation.my_home_screen

import com.example.myhome.realm.model.CameraRealm

sealed class MyHomeEvent {
	data class CameraIsFavouriteToggled(val camera: CameraRealm) : MyHomeEvent()
	object CamerasPullRefreshed : MyHomeEvent()
}