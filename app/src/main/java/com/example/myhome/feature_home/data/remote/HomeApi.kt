package com.example.myhome.feature_home.data.remote

import com.example.myhome.feature_home.data.remote.model.camera.CamerasResponse

interface HomeApi {

	suspend fun getCameras(): CamerasResponse

}