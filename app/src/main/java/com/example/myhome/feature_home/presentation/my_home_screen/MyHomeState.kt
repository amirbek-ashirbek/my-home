package com.example.myhome.feature_home.presentation.my_home_screen

import com.example.myhome.feature_home.domain.model.Camera

data class MyHomeState(
	val cameras: Map<String?, List<Camera>>? = null,
	val camerasAreLoading: Boolean = false,
	val camerasError: Boolean = false
)