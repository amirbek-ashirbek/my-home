package com.example.myhome.feature_home.presentation.my_home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.feature_home.domain.use_case.camera.ChangeCameraIsFavouriteUseCase
import com.example.myhome.feature_home.domain.use_case.door.ChangeDoorIsFavouriteUseCase
import com.example.myhome.feature_home.domain.use_case.door.ChangeDoorIsLockedUseCase
import com.example.myhome.feature_home.domain.use_case.camera.GetCamerasUseCase
import com.example.myhome.feature_home.domain.use_case.door.GetDoorsUseCase
import com.example.myhome.realm.model.Camera
import com.example.myhome.realm.model.Door
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHomeViewModel @Inject constructor(
	private val getCamerasUseCase: GetCamerasUseCase,
	private val changeCameraIsFavouriteUseCase: ChangeCameraIsFavouriteUseCase,
	private val getDoorsUseCase: GetDoorsUseCase,
	private val changeDoorIsFavouriteUseCase: ChangeDoorIsFavouriteUseCase,
	private val changeDoorIsLockedUseCase: ChangeDoorIsLockedUseCase
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
			is MyHomeEvent.DoorIsFavouriteToggled -> handleDoorFavouriteToggled(event.door)
			is MyHomeEvent.DoorIsLockedToggled -> handleDoorIsLockedToggled(event.door)
			is MyHomeEvent.DoorLockClicked -> {
				_uiState.value = _uiState.value?.copy(
					doorLockDialogIsVisible = true,
					lockClickedDoor = event.door
				)
			}
			is MyHomeEvent.DoorLockDialogDismissed -> {
				_uiState.value = _uiState.value?.copy(doorLockDialogIsVisible = false)
			}
		}
	}

	private fun getCameras() {
		_uiState.value = _uiState.value?.copy(camerasAreLoading = true, camerasError = false)
		updateCamerasState()
	}

	private fun updateCamerasState() {
		viewModelScope.launch {
			getCamerasUseCase.execute().collect { cameras ->
				val camerasGroupedByRoom: Map<String?, List<Camera>> = cameras.groupBy { it.room }
				_uiState.value = _uiState.value?.copy(
					cameras = camerasGroupedByRoom,
					camerasAreLoading = false,
					camerasError = cameras.isEmpty()
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
		_uiState.value = _uiState.value?.copy(doorsAreLoading = true, doorsError = false)
		updateDoorsState()
	}

	private fun updateDoorsState() {
		viewModelScope.launch {
			getDoorsUseCase.execute().collect { doors ->
				_uiState.value = _uiState.value?.copy(
					doors = doors,
					doorsAreLoading = false,
					doorsError = doors.isEmpty()
				)
			}
		}
	}

	private fun handleDoorFavouriteToggled(door: Door) {
		_uiState.value = _uiState.value?.copy(isFavouriteToggledDoor = door)
		viewModelScope.launch {
			changeDoorIsFavouriteUseCase.execute(door)
			updateDoorsState()
		}
	}

	private fun handleDoorIsLockedToggled(door: Door) {
		_uiState.value = _uiState.value?.copy(lockClickedDoor = door)
		viewModelScope.launch {
			changeDoorIsLockedUseCase.execute(door)
			updateDoorsState()
		}
		_uiState.value = _uiState.value?.copy(doorLockDialogIsVisible = false)
	}

}