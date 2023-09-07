package com.example.myhome.feature_home.presentation.my_home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myhome.R

@Composable
fun DoorItem(
	name: String,
	snapshot: String?,
	isLocked: Boolean,
	modifier : Modifier = Modifier
) {
	Card(
		shape = RoundedCornerShape(12.dp),
		elevation = 3.dp,
		backgroundColor = MaterialTheme.colors.background,
		modifier = Modifier
			.fillMaxWidth()
	) {
		Column {
			if (snapshot != null) {
				AsyncImage(
					model = snapshot,
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
				Text(
					text = name
				)
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