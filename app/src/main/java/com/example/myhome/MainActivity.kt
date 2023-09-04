package com.example.myhome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myhome.feature_home.presentation.my_home_screen.MyHomeScreen
import com.example.myhome.feature_home.presentation.my_home_screen.MyHomeState
import com.example.myhome.feature_home.presentation.my_home_screen.MyHomeViewModel
import com.example.myhome.ui.theme.MyHomeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		enableEdgeToEdge()
		super.onCreate(savedInstanceState)

		setContent {
			MyHomeTheme {
				val navController = rememberNavController()
				Box(
					modifier = Modifier
						.fillMaxSize()
						.systemBarsPadding()
				) {
					MyHomeNavHost(
						navController = navController
					)
				}
			}
		}
	}
}

@Composable
fun MyHomeNavHost(
	navController: NavHostController,
	modifier: Modifier = Modifier
) {
	NavHost(
		navController = navController,
		startDestination = "main"
	) {
		composable("main") {
			MainScreen(
				onNavigateToMyHome = { navController.navigate("my_home") }
			)
		}
		composable("my_home") {
			val viewModel = hiltViewModel<MyHomeViewModel>()
			val uiState = viewModel.uiState.observeAsState(initial = MyHomeState()).value
			MyHomeScreen(
				uiState = uiState,
				onMyHomeEvent = viewModel::onEvent
			)
		}
	}
}

@Composable
fun MainScreen(
	onNavigateToMyHome: () -> Unit
) {
	Surface(
		modifier = Modifier.fillMaxSize()
	) {
		Box(
			modifier = Modifier
				.background(color = Color.Red)
				.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			Button(
				onClick = onNavigateToMyHome,
				modifier = Modifier.padding(16.dp)
			) {
				Text(text = "вперееед")
			}
		}
	}
}