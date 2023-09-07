package com.example.myhome.feature_home.presentation.my_home_screen.components

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myhome.R
import kotlin.math.roundToInt

@Composable
fun CameraItem(
	name: String,
	snapshot: String,
	isRecording: Boolean,
	isFavourite: Boolean,
	isFromDatabase: Boolean,
	modifier: Modifier = Modifier
) {
	var isRevealed by remember { mutableStateOf(false) }
	val transitionState = remember {
		MutableTransitionState(isRevealed).apply {
			targetState = !isRevealed
		}
	}
	val transition = updateTransition(transitionState, label = "")
	val offsetTransition by transition.animateFloat(
		label = "cameraOffsetTransition",
		transitionSpec = { tween(durationMillis = 500) },
		targetValueByState = { if (isRevealed) -150f else 0f },
	)

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.offset { IntOffset((offsetTransition).roundToInt(), 0) }
			.clickable(
				onClick = {
					isRevealed = !isRevealed
				}
			)
	) {
		Card(
			shape = RoundedCornerShape(12.dp),
			elevation = 1.dp,
			backgroundColor = MaterialTheme.colors.background,
			modifier = Modifier
				.fillMaxSize()
		) {
			Column {
				Box(
					modifier = Modifier
						.fillMaxSize()
				) {
					if (isFromDatabase) {
						Image(
							painter = painterResource(id = R.drawable.camera_image),
							contentDescription = null,
							contentScale = ContentScale.FillBounds,
							modifier = Modifier
								.fillMaxWidth()
								.height(207.dp)
						)
					} else {
						AsyncImage(
							model = snapshot,
							contentDescription = null,
							contentScale = ContentScale.FillBounds,
							modifier = Modifier
								.fillMaxWidth()
								.height(207.dp)
						)
					}
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
}