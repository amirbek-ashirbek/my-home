package com.example.myhome.feature_home.presentation.my_home_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.feature_home.domain.use_case.ChangeCameraIsFavouriteUseCase
import com.example.myhome.feature_home.domain.use_case.GetCamerasUseCase
import com.example.myhome.realm.model.CameraRealm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHomeViewModel @Inject constructor(
	private val getCamerasUseCase: GetCamerasUseCase,
	private val changeCameraIsFavouriteUseCase: ChangeCameraIsFavouriteUseCase
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
			is MyHomeEvent.CameraIsFavouriteToggled -> {
				_uiState.value = _uiState.value?.copy(isFavouriteToggledCamera = event.camera)
				_uiState.value?.isFavouriteToggledCamera?.let {
					changeCameraIsFavourite(it)
				}
				getCameras()
				Log.d("FavouriteToggledCamera","yoo ${_uiState.value?.isFavouriteToggledCamera}")
			}
		}
	}

	private fun getCameras() {

		_uiState.value = _uiState.value?.copy(camerasAreLoading = true)

		viewModelScope.launch {
			getCamerasUseCase.execute().collect { cameras ->
				val camerasGroupedByRoom: Map<String?, List<CameraRealm>> = cameras.groupBy { it.room }
				_uiState.value = _uiState.value?.copy(
					cameras = camerasGroupedByRoom,
					camerasAreLoading = false
				)
			}
		}
	}

	private fun changeCameraIsFavourite(camera: CameraRealm) {
		viewModelScope.launch {
			changeCameraIsFavouriteUseCase.execute(camera = camera)
		}
	}

}