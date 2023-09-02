package com.example.myhome.feature_home.presentation.my_home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.feature_home.domain.use_case.GetCamerasUseCase
import com.example.myhome.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHomeViewModel @Inject constructor(
	private val getCamerasUseCase: GetCamerasUseCase
) : ViewModel() {

	private val _uiState = MutableLiveData(
		MyHomeState(camerasAreLoading = true)
	)
	val uiState: LiveData<MyHomeState> = _uiState

	init {
		getCameras()
	}

	fun onEvent(event: MyHomeEvent) {
		when (event) {
			is MyHomeEvent.CamerasPullRefreshed -> {
				getCameras()
			}
		}
	}

	private fun getCameras() {

		_uiState.value = _uiState.value?.copy(camerasAreLoading = true)

		viewModelScope.launch {
			getCamerasUseCase.execute().collect { response ->
				when (response) {
					is Response.Success -> {
						val cameras = response.data
						val camerasByRoom = cameras?.groupBy { camera ->
							camera.room
						}
						_uiState.value = _uiState.value?.copy(
							cameras = camerasByRoom,
							camerasError = false,
							camerasAreLoading = false
						)
					}
					is Response.Error -> {
						_uiState.value = _uiState.value?.copy(
							camerasError = true,
							camerasAreLoading = false
						)
					}
				}
			}
		}
	}
}