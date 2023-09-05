package com.example.myhome.feature_home.presentation.my_home_screen

import com.example.myhome.realm.model.CameraRealm

data class MyHomeState(
	val cameras: Map<String?, List<CameraRealm>>? = null,
	val isFavouriteToggledCamera: CameraRealm? = null,
	val camerasAreLoading: Boolean = false,
	val camerasError: Boolean = false
)