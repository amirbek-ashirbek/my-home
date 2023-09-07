package com.example.myhome.feature_home.data.remote

import com.example.myhome.feature_home.data.remote.model.camera.CamerasResponse
import com.example.myhome.feature_home.data.remote.model.door.DoorsResponse

interface HomeApi {

	suspend fun getCameras(): CamerasResponse

	suspend fun getDoors(): DoorsResponse

}