package com.example.myhome.feature_home.presentation.my_home_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myhome.R
import com.example.myhome.feature_home.domain.model.Camera
import com.example.myhome.feature_home.presentation.my_home_screen.components.CameraItem
import com.example.myhome.feature_home.presentation.my_home_screen.components.MyHomeHeader
import com.example.myhome.ui.theme.Blue500
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
			Spacer(modifier = Modifier.height(64.dp))
			TabRow(
				selectedTabIndex = pagerState.currentPage,
				backgroundColor = MaterialTheme.colors.surface,
				indicator = { tabPositions ->
					TabRowDefaults.Indicator(
						Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
						color = Blue500
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
							onCamerasRefreshed = { onMyHomeEvent(MyHomeEvent.CamerasPullRefreshed) }
						)
					}
					1 -> {
						Box(
							modifier = Modifier.fillMaxSize().background(color = Color.Green)
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
	onCamerasRefreshed: () -> Unit
) {

	val camerasPullRefreshState = rememberPullRefreshState(
		refreshing = camerasAreLoading,
		onRefresh = onCamerasRefreshed
	)

	Box(
		modifier = Modifier
			.pullRefresh(camerasPullRefreshState)
			.padding(top = 16.dp)
	) {
		if (camerasAreLoading) {
			Box(modifier = Modifier.fillMaxSize()) {
				CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
			}
		} else {
			LazyColumn(
				modifier = Modifier
					.padding(horizontal = 21.dp)
			) {
				cameras?.forEach { (room, camerasByRoom) ->
					if (room != null) {
						item {
							Text(
								text = room,
								style = MaterialTheme.typography.h1.copy(
									fontWeight = FontWeight.Light
								)
							)
							Spacer(modifier = Modifier.height(12.dp))
						}
						items(camerasByRoom) { camera ->
							CameraItem(
								name = camera.name,
								snapshot = camera.snapshot,
								isRecording = camera.isRecording,
								isFavourite = camera.isFavourite
							)
							Spacer(modifier = Modifier.height(12.dp))
						}
					}
				}
			}
			PullRefreshIndicator(
				refreshing = camerasAreLoading,
				state = camerasPullRefreshState,
				modifier = Modifier
					.align(TopCenter)
			)
		}
	}
}

@Composable
fun DoorsTabContent() {

}