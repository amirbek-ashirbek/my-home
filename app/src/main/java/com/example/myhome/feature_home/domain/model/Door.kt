package com.example.myhome.feature_home.domain.model

data class Door(
	val doorId: Int,
	val name: String,
	val room: String,
	val snapshot: String?,
	val isFavourite: Boolean
)