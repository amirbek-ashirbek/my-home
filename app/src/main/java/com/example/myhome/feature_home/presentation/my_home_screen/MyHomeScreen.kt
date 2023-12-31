package com.example.myhome.feature_home.presentation.my_home_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhome.R
import com.example.myhome.feature_home.presentation.my_home_screen.components.ButtonFavourite
import com.example.myhome.feature_home.presentation.my_home_screen.components.camera.CameraItem
import com.example.myhome.feature_home.presentation.my_home_screen.components.MyHomeHeader
import com.example.myhome.feature_home.presentation.my_home_screen.components.common.ErrorMessage
import com.example.myhome.feature_home.presentation.my_home_screen.components.door.DoorActionsRow
import com.example.myhome.feature_home.presentation.my_home_screen.components.door.DoorEditNameDialog
import com.example.myhome.feature_home.presentation.my_home_screen.components.door.DoorItem
import com.example.myhome.feature_home.presentation.my_home_screen.components.door.DoorLockDialog
import com.example.myhome.realm.model.Camera
import com.example.myhome.realm.model.Door
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyHomeScreen(
	uiState: MyHomeState,
	onMyHomeEvent:(MyHomeEvent) -> Unit
) {

	val tabs = listOf(
		MyHomeTab.Cameras,
		MyHomeTab.Doors
	)
	val scope = rememberCoroutineScope()
	val pagerState = rememberPagerState(
		initialPage = 0,
		initialPageOffsetFraction = 0f
	) { 2 }

	val doorLockDialogIsVisible = uiState.doorLockDialogIsVisible
	val doorEditNameDialogIsVisible = uiState.doorEditNameDialogIsVisible

	if (doorLockDialogIsVisible) {

		val door = uiState.lockClickedDoor

		if (door != null) {
			val lock = stringResource(id = R.string.lock)
			val unlock = stringResource(id = R.string.unlock)
			val action = if (door.isLocked) unlock else lock
			val dialogTitle = stringResource(id = R.string.confirm_action)
			val dialogText = stringResource(id = R.string.lock_are_you_sure, action, "\"${door.name}\"")

			DoorLockDialog(
				dialogTitle = dialogTitle,
				dialogText = dialogText,
				onDismissRequest = { onMyHomeEvent(MyHomeEvent.DoorLockDialogDismissed) },
				onConfirmClicked = { onMyHomeEvent(MyHomeEvent.DoorIsLockedToggled(door = door)) }
			)
		}
	}

	if (doorEditNameDialogIsVisible) {
		uiState.editClickedDoor?.let { door ->
			DoorEditNameDialog(
				doorName = uiState.doorNameOnEdit ?: "",
				onDoorNameTextChanged = { doorName ->
					onMyHomeEvent(MyHomeEvent.DoorNameEdited(doorName))
				},
				onDoorNameChangeConfirmed = { onMyHomeEvent(MyHomeEvent.DoorNameEditConfirmed) },
				onDismiss = { onMyHomeEvent(MyHomeEvent.DoorEditNameDialogDismissed) }
			)
		}
	}

	Surface(
		color = MaterialTheme.colors.surface,
		modifier = Modifier
			.fillMaxSize()
	) {
		Column {
			Spacer(modifier = Modifier.height(60.dp))
			MyHomeHeader(
				text = stringResource(id = R.string.my_home),
				modifier = Modifier
					.align(CenterHorizontally)
			)
			Spacer(modifier = Modifier.height(20.dp))
			TabRow(
				selectedTabIndex = pagerState.currentPage,
				backgroundColor = MaterialTheme.colors.surface,
				indicator = { tabPositions ->
					TabRowDefaults.Indicator(
						Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
						color = MaterialTheme.colors.primary
					)
				}
			) {
				tabs.forEachIndexed { index, tab ->
					Tab(
						text = {
							Text(
								text = stringResource(id = tab.title),
								style = MaterialTheme.typography.h1.copy(fontSize = 17.sp)
							)
						},
						selected = pagerState.currentPage == index,
						onClick = {
							scope.launch {
								pagerState.animateScrollToPage(index)
							}
						}
					)
				}
			}
			HorizontalPager(
				state = pagerState,
			) { index ->
				when (index) {
					0 -> {
						CamerasTabContent(
							cameras = uiState.cameras,
							camerasAreLoading = uiState.camerasAreLoading,
							camerasError = uiState.camerasError,
							onCamerasRefreshed = { onMyHomeEvent(MyHomeEvent.CamerasPullRefreshed) },
							onIsFavouriteButtonClicked = { camera ->
								onMyHomeEvent(MyHomeEvent.CameraIsFavouriteToggled(camera = camera))
							},
							onRetryClicked = { onMyHomeEvent(MyHomeEvent.RetryClicked) }
						)
					}
					1 -> {
						DoorsTabContent(
							doors = uiState.doors,
							doorsAreLoading = uiState.doorsAreLoading,
							doorsError = uiState.doorsError,
							onDoorsRefreshed = { onMyHomeEvent(MyHomeEvent.DoorsPullRefreshed) },
							onIsFavouriteButtonClicked = { door ->
								onMyHomeEvent(MyHomeEvent.DoorIsFavouriteToggled(door = door))
							},
							onLockClicked = {  door ->
								onMyHomeEvent(MyHomeEvent.DoorLockClicked(door = door))
							},
							onEditClicked = { door ->
								onMyHomeEvent(MyHomeEvent.DoorEditClicked(door = door))
							},
							onRetryClicked = { onMyHomeEvent(MyHomeEvent.RetryClicked) }
						)
					}
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CamerasTabContent(
	cameras: Map<String?, List<Camera>>?,
	camerasAreLoading: Boolean,
	camerasError: Boolean,
	onCamerasRefreshed: () -> Unit,
	onRetryClicked: () -> Unit,
	onIsFavouriteButtonClicked: (Camera) -> Unit
) {

	val camerasPullRefreshState = rememberPullRefreshState(
		refreshing = camerasAreLoading,
		onRefresh = onCamerasRefreshed
	)

	val scrollState = rememberScrollState()

	var isFavouriteButtonWidth by remember { mutableStateOf(0) }

	Box(
		modifier = Modifier
			.fillMaxSize()
			.pullRefresh(camerasPullRefreshState)
	) {
		if (camerasError) {
			Box(
				modifier = Modifier.fillMaxSize()
			) {
				ErrorMessage(
					onRetry = onRetryClicked,
					modifier = Modifier
						.align(Center)
				)
			}
		}
		if (camerasAreLoading) {
			Box(modifier = Modifier.fillMaxSize()) {
				CircularProgressIndicator(modifier = Modifier.align(Center))
			}
		} else {
			Column(
				modifier = Modifier
					.padding(horizontal = 21.dp)
					.verticalScroll(state = scrollState)
			) {
				Spacer(modifier = Modifier.height(16.dp))
				cameras?.forEach { (room, camerasByRoom) ->
					if (!room.isNullOrEmpty()) {
						Text(
							text = room,
							style = MaterialTheme.typography.h1.copy(
								fontWeight = FontWeight.Light
							)
						)
						Spacer(modifier = Modifier.height(12.dp))
						camerasByRoom.forEach { camera ->
							Box {
								ButtonFavourite(
									isFavourite = camera.isFavourite,
									onClick = { onIsFavouriteButtonClicked(camera) },
									modifier = Modifier
										.align(CenterEnd)
										.onGloballyPositioned { coordinates ->
											isFavouriteButtonWidth = coordinates.size.width
										}
								)
								CameraItem(
									name = camera.name,
									snapshot = camera.snapshot,
									isRecording = camera.isRecording,
									isFavourite = camera.isFavourite,
									isFromDatabase = camera.isFromDatabase,
									offset = -(isFavouriteButtonWidth.dp + 32.dp).value
								)
							}
							Spacer(modifier = Modifier.height(12.dp))
						}
					}
				}
			}
			PullRefreshIndicator(
				refreshing = camerasAreLoading,
				state = camerasPullRefreshState,
				contentColor = MaterialTheme.colors.primary,
				modifier = Modifier
					.align(TopCenter)
			)
		}
	}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoorsTabContent(
	doors: List<Door>?,
	doorsAreLoading: Boolean,
	doorsError: Boolean,
	onDoorsRefreshed: () -> Unit,
	onRetryClicked: () -> Unit,
	onIsFavouriteButtonClicked: (Door) -> Unit,
	onLockClicked: (Door) -> Unit,
	onEditClicked: (Door) -> Unit,
	modifier: Modifier = Modifier
) {

	val doorsPullRefreshState = rememberPullRefreshState(
		refreshing = doorsAreLoading,
		onRefresh = onDoorsRefreshed
	)
	val scrollState = rememberScrollState()

	var doorActionsRowWidth by remember { mutableStateOf(0) }

	Box(
		modifier = Modifier
			.fillMaxSize()
			.pullRefresh(state = doorsPullRefreshState)
	) {
		if (doorsError) {
			Box(
				modifier = Modifier.fillMaxSize()
			) {
				ErrorMessage(
					onRetry = onRetryClicked,
					modifier = Modifier
						.align(Center)
				)
			}
		}
		if (doorsAreLoading) {
			Box(modifier = Modifier.fillMaxSize()) {
				CircularProgressIndicator(modifier = Modifier.align(Center))
			}
		} else {
			Column(
				modifier = Modifier
					.padding(horizontal = 21.dp)
					.verticalScroll(state = scrollState)
			) {
				Spacer(modifier = Modifier.height(18.dp))
				doors?.forEach { door ->
					Box {
						DoorActionsRow(
							isFavourite = door.isFavourite,
							onEditButtonClicked = { onEditClicked(door) },
							onIsFavouriteButtonClicked = { onIsFavouriteButtonClicked(door) },
							modifier = Modifier
								.align(CenterEnd)
								.onGloballyPositioned { coordinates ->
									doorActionsRowWidth = coordinates.size.width
								}
						)
						DoorItem(
							name = door.name,
							snapshot = door.snapshot,
							isLocked = door.isLocked,
							isFromDatabase = door.isFromDatabase,
							onLockClicked = { onLockClicked(door) },
							offset = -(doorActionsRowWidth.dp + 32.dp).value
						)
					}
					Spacer(modifier = Modifier.height(11.dp))
				}
			}
			PullRefreshIndicator(
				refreshing = doorsAreLoading,
				state = doorsPullRefreshState,
				contentColor = MaterialTheme.colors.primary,
				modifier = Modifier
					.align(TopCenter)
			)
		}
	}
}