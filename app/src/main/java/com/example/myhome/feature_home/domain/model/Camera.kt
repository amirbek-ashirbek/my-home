package com.example.myhome.feature_home.domain.model

data class Camera(
	val cameraId: Int,
	val name: String,
	val room: String?,
	val snapshot: String,
	val isFavourite: Boolean,
	val isRecording: Boolean
)
