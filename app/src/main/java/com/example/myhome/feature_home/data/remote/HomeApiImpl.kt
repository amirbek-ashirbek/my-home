package com.example.myhome.feature_home.data.remote

import com.example.myhome.feature_home.data.remote.model.camera.CamerasResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class HomeApiImpl @Inject constructor(
	private val client: HttpClient
) : HomeApi {

	override suspend fun getCameras(): CamerasResponse {
		return client.get(urlString = HttpRoutes.CAMERAS).body()
	}

}