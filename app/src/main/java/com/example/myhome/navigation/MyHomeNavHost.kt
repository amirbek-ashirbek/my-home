package com.example.myhome.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myhome.feature_home.presentation.my_home_screen.MyHomeScreen
import com.example.myhome.feature_home.presentation.my_home_screen.MyHomeState
import com.example.myhome.feature_home.presentation.my_home_screen.MyHomeViewModel

@Composable
fun MyHomeNavHost(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	NavHost(
		navController = navController,
		startDestination = MyHomeScreen.route,
		modifier = modifier
	) {
		composable(route = MyHomeScreen.route) {
			val viewModel = hiltViewModel<MyHomeViewModel>()
			val uiState = viewModel.uiState.observeAsState(initial = MyHomeState()).value
			MyHomeScreen(
				uiState = uiState,
				onMyHomeEvent = viewModel::onEvent
			)
		}
	}
}