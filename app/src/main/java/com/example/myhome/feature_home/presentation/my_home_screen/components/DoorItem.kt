package com.example.myhome.feature_home.presentation.my_home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myhome.R

@Composable
fun DoorItem(
	name: String,
	snapshot: String?,
) {
	Card(
		shape = RoundedCornerShape(12.dp),
		elevation = 3.dp,
		modifier = Modifier
			.fillMaxWidth()
	) {
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
			Image(
				painter = painterResource(id = R.drawable.icon_lock),
				contentDescription = "Lock icon"
			)
		}
	}
}