package com.example.myhome.feature_home.presentation.my_home_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myhome.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CameraItem(
	name: String,
	snapshot: String,
	isRecording: Boolean,
	isFavourite: Boolean,
	onCameraLongClicked: () -> Unit,
	modifier: Modifier = Modifier
) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.shadow(
				elevation = 2.dp,
				shape = RoundedCornerShape(12.dp)
			)
			.background(
				color = Color.White,
				shape = RoundedCornerShape(12.dp)
			)
			.combinedClickable(
				onClick = { },
				onLongClick = { onCameraLongClicked() }
			)
	) {
		Column {
			Box(
				modifier = Modifier
					.fillMaxSize()
			) {
				AsyncImage(
					model = snapshot,
					contentDescription = null,
					contentScale = ContentScale.FillBounds,
					modifier = Modifier
						.fillMaxWidth()
						.height(207.dp)
				)
				if (isRecording) {
					Box(
						modifier = Modifier
							.align(Alignment.TopStart)
							.padding(start = 8.dp, top = 8.dp)
					) {
						Image(
							painter = painterResource(id = R.drawable.rec),
							contentDescription = "Recording symbol"
						)
					}
				}
				if (isFavourite) {
					Box(
						modifier = Modifier
							.align(Alignment.TopEnd)
							.padding(end = 8.dp, top = 8.dp)
					) {
						Image(
							painter = painterResource(id = R.drawable.star),
							contentDescription = "Favourites star symbol"
						)
					}
				}
			}
			Spacer(modifier = Modifier.height(22.dp))
			Text(
				text = name,
				modifier = Modifier
					.padding(start = 18.dp)
			)
			Spacer(modifier = Modifier
				.height(20.dp)
			)
		}
	}
}