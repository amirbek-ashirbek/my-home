package com.example.myhome.feature_home.presentation.my_home_screen.components.door

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myhome.R

@Composable
fun DoorLockDialog(
	dialogTitle: String,
	dialogText: String,
	onDismissRequest: () -> Unit,
	onConfirmClicked: () -> Unit,
	modifier: Modifier = Modifier
) {

	AlertDialog(
		title = {
			Text(
				text = dialogTitle,
				style = MaterialTheme.typography.h1
			)
		},
		text = {
			Text(
				text = dialogText,
				style = MaterialTheme.typography.body1
			)
		},
		confirmButton = {
			TextButton(
				onClick = onConfirmClicked
			) {
				Text(
					text = stringResource(id = R.string.yes)
				)
			}
		},
		dismissButton = {
			TextButton(
				onClick = onDismissRequest
			) {
				Text(
					text = stringResource(id = R.string.cancel)
				)
			}
		},
		onDismissRequest = onDismissRequest
	)

}