package com.example.myhome.feature_home.presentation.my_home_screen.components.door

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myhome.feature_home.presentation.my_home_screen.components.ButtonFavourite

@Composable
fun DoorActionsRow(
	isFavourite: Boolean,
	onIsFavouriteButtonClicked: () -> Unit,
	onEditButtonClicked: () -> Unit,
	modifier: Modifier = Modifier
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier
	) {
		ButtonEdit(
			onClick = onEditButtonClicked
		)
		Spacer(modifier = Modifier.width(9.dp))
		ButtonFavourite(
			isFavourite = isFavourite,
			onClick = onIsFavouriteButtonClicked
		)
	}
}