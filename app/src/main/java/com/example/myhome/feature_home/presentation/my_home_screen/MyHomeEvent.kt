package com.example.myhome.feature_home.presentation.my_home_screen

sealed class MyHomeEvent {
	object CamerasPullRefreshed : MyHomeEvent()
	object GetCamerasFromDatabaseButtonClicked : MyHomeEvent()
}