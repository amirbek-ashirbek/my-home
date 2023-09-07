package com.example.myhome.feature_home.presentation.my_home_screen.components.door

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myhome.R
import com.example.myhome.ui.theme.Gray500
import kotlin.math.roundToInt

@Composable
fun DoorItem(
	name: String,
	snapshot: String?,
	isLocked: Boolean,
	isFromDatabase: Boolean,
	onLockClicked: () -> Unit,
	modifier : Modifier = Modifier
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
		targetValueByState = { if (isRevealed) -250f else 0f },
	)

	Card(
		shape = RoundedCornerShape(12.dp),
		elevation = 1.dp,
		backgroundColor = MaterialTheme.colors.background,
		modifier = Modifier
			.fillMaxWidth()
			.offset { IntOffset((offsetTransition).roundToInt(), 0) }
			.clickable(
				onClick = {
					isRevealed = !isRevealed
				}
			)
	) {
		Column {
			if (snapshot != null && !isFromDatabase) {
				AsyncImage(
					model = snapshot,
					contentDescription = null,
					contentScale = ContentScale.FillBounds,
					modifier = Modifier
						.fillMaxWidth()
						.height(207.dp)
				)
			}
			if (snapshot != null && isFromDatabase) {
				Image(
					painter = painterResource(id = R.drawable.door_image),
					contentDescription = null,
					contentScale = ContentScale.FillBounds,
					modifier = Modifier
						.fillMaxWidth()
						.height(207.dp)
				)
			}
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween,
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 21.dp, bottom = 21.dp, start = 16.dp, end = 24.dp)
			) {
				Column {
					Text(
						text = name
					)
					if (snapshot != null) {
						Text(
							text = stringResource(id = R.string.online),
							style = MaterialTheme.typography.body1.copy(
								fontSize = 14.sp,
								fontWeight = FontWeight.Light,
								color = Gray500
							)
						)
					}
				}
				if (isFromDatabase) {
					Box(
						modifier = Modifier
							.clickable(
								onClick = onLockClicked
							)
					) {
						if (isLocked) {
							Image(
								painter = painterResource(id = R.drawable.icon_lock),
								contentDescription = "Lock icon"
							)
						} else {
							Image(
								painter = painterResource(id = R.drawable.icon_lock_unlocked),
								contentDescription = "Lock icon"
							)
						}
					}
				}
			}
		}
	}
}