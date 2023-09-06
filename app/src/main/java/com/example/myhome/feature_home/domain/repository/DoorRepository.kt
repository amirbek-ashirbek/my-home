package com.example.myhome.feature_home.domain.repository

import com.example.myhome.realm.model.Door
import com.example.myhome.util.Response
import kotlinx.coroutines.flow.Flow

interface DoorRepository {

	fun getDoorsFromNetwork(): Flow<Response<List<Door>>>

}