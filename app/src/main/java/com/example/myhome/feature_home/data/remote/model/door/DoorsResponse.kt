package com.example.myhome.feature_home.data.remote.model.door


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DoorsResponse(
	@SerialName("data")
    val `data`: List<DoorsData>,
	@SerialName("success")
    val success: Boolean
)