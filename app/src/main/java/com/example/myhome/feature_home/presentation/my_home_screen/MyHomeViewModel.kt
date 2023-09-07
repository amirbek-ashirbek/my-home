package com.example.myhome.feature_home.presentation.my_home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.feature_home.domain.use_case.ChangeCameraIsFavouriteUseCase
import com.example.myhome.feature_home.domain.use_case.GetCamerasUseCase
import com.example.myhome.feature_home.domain.use_case.GetDoorsUseCase
import com.example.myhome.realm.model.Camera
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHomeViewModel @Inject constructor(
	private val getCamerasUseCase: GetCamerasUseCase,
	private val changeCameraIsFavouriteUseCase: ChangeCameraIsFavouriteUseCase,
	private val getDoorsUseCase: GetDoorsUseCase
) : ViewModel() {

	private val _uiState = MutableLiveData(
		MyHomeState(
			camerasAreLoading = true,
			doorsAreLoading = true
		)
	)
	val uiState: LiveData<MyHomeState> = _uiState

	init {
		getCameras()
		getDoors()
	}

	fun onEvent(event: MyHomeEvent) {
		when (event) {
			is MyHomeEvent.CamerasPullRefreshed -> getCameras()
			is MyHomeEvent.CameraIsFavouriteToggled -> handleCameraFavouriteToggled(event.camera)
			is MyHomeEvent.DoorsPullRefreshed -> getDoors()
		}
	}

	private fun getCameras() {
		_uiState.value = _uiState.value?.copy(camerasAreLoading = true)
		updateCamerasState()
	}

	private fun updateCamerasState() {
		viewModelScope.launch {
			getCamerasUseCase.execute().collect { cameras ->
				val camerasGroupedByRoom: Map<String?, List<Camera>> = cameras.groupBy { it.room }
				_uiState.value = _uiState.value?.copy(
					cameras = camerasGroupedByRoom,
					camerasAreLoading = false
				)
			}
		}
	}

	private fun handleCameraFavouriteToggled(camera: Camera) {
		_uiState.value = _uiState.value?.copy(isFavouriteToggledCamera = camera)
		viewModelScope.launch {
			changeCameraIsFavouriteUseCase.execute(camera)
			updateCamerasState()
		}
	}

	private fun getDoors() {
		_uiState.value = _uiState.value?.copy(doorsAreLoading = true)
		updateDoorsState()
	}

	private fun updateDoorsState() {
		viewModelScope.launch {
			getDoorsUseCase.execute().collect { doors ->
				_uiState.value = _uiState.value?.copy(
					doors = doors,
					doorsAreLoading = false
				)
			}
		}
	}

}