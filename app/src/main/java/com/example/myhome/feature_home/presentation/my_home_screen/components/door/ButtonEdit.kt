package com.example.myhome.feature_home.presentation.my_home_screen.components.door

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.example.myhome.R

@Composable
fun ButtonEdit(
	onClick: () -> Unit
) {
	Box(
		modifier = Modifier
			.clip(CircleShape)
			.clickable(
				onClick = onClick
			)
	) {
		Image(
			painter = painterResource(id = R.drawable.icon_edit),
			contentDescription = "Edit icon"
		)
	}
}