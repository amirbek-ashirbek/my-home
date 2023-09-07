package com.example.myhome.feature_home.presentation.my_home_screen

import com.example.myhome.realm.model.Camera
import com.example.myhome.realm.model.Door

data class MyHomeState(
	val cameras: Map<String?, List<Camera>>? = null,
	val isFavouriteToggledCamera: Camera? = null,
	val camerasAreLoading: Boolean = false,
	val camerasError: Boolean = false,
	val doors: List<Door>? = null,
	val isFavouriteToggledDoor: Door? = null,
	val lockClickedDoor: Door? = null,
	val doorsAreLoading: Boolean = false,
	val doorsError: Boolean = false,
	val doorLockDialogIsVisible: Boolean = false
)