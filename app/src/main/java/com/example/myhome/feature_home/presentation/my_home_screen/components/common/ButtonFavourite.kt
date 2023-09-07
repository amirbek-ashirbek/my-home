package com.example.myhome.feature_home.presentation.my_home_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myhome.R

@Composable
fun ButtonFavourite(
	isFavourite: Boolean,
	onClick: () -> Unit,
	modifier: Modifier = Modifier
) {
	Box(
		modifier = modifier
			.size(36.dp)
			.border(
				width = 0.3.dp,
				color = Color(0xFFDBDBDB),
				shape = CircleShape
			)
			.clip(CircleShape)
			.clickable(
				onClick = { onClick() }
			)
	) {
		Image(
			painter = painterResource(
				id = if (isFavourite) R.drawable.star else R.drawable.star_empty_svg
			),
			contentDescription = "Favourite button",
			modifier = Modifier
				.size(size = if (isFavourite) 24.dp else 20.dp)
				.align(Alignment.Center)
		)
	}
}