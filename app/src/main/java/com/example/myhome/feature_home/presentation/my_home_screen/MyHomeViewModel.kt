package com.example.myhome.feature_home.presentation.my_home_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.feature_home.domain.repository.CameraRepository
import com.example.myhome.feature_home.domain.use_case.GetCamerasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHomeViewModel @Inject constructor(
	private val getCamerasUseCase: GetCamerasUseCase,
	private val cameraRepository: CameraRepository
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
			is MyHomeEvent.GetCamerasFromDatabaseButtonClicked -> {
				getCamerasFromDatabase()
			}
		}
	}

	private fun getCameras() {

		_uiState.value = _uiState.value?.copy(camerasAreLoading = true)

		viewModelScope.launch {
			getCamerasUseCase.execute().collect { cameras ->
				val camerasGroupedByRoom = cameras.groupBy { it.room }
				_uiState.value = _uiState.value?.copy(
					cameras = camerasGroupedByRoom,
					camerasAreLoading = false
				)
			}
		}
	}


	private fun getCamerasFromDatabase() {
		viewModelScope.launch {
			cameraRepository.getCamerasFromDatabase().collect {
				_uiState.value = _uiState.value?.copy(
					camerasTest = it
				)
				Log.d("MyHomeViewModel", "cameras from database: ${_uiState.value?.camerasTest}")
			}
		}
	}

}