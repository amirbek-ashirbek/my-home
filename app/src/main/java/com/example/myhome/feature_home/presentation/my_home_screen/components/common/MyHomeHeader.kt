package com.example.myhome.feature_home.presentation.my_home_screen.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MyHomeHeader(
	text: String,
	modifier: Modifier = Modifier
) {
	Text(
		text = text,
		style = MaterialTheme.typography.h1,
		textAlign = TextAlign.Center,
		modifier = modifier
	)
}