package com.example.myhome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.myhome.navigation.MyHomeNavHost
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