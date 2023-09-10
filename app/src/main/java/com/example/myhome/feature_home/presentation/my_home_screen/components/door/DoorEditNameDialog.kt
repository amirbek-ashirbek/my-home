package com.example.myhome.feature_home.presentation.my_home_screen.components.door

import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.myhome.R
import com.example.myhome.feature_home.presentation.my_home_screen.components.common.CustomDialog

@Composable
fun DoorEditNameDialog(
	doorName: String,
	onDoorNameTextChanged: (String) -> Unit,
	onDoorNameChangeConfirmed: () -> Unit,
	onDismiss: () -> Unit
) {
	// Arbitrary number
	val maxChar = 30

	CustomDialog(
		title = {
			Text(
				text = stringResource(id = R.string.choose_name),
				style = MaterialTheme.typography.h1
			)
		},
		content = {
			OutlinedTextField(
				value = doorName,
				onValueChange = {
					onDoorNameTextChanged(it.take(maxChar))
				},
				singleLine = true
			) },
		confirmButton = {
			TextButton(onClick = onDoorNameChangeConfirmed) {
				Text(
					text = stringResource(id = R.string.confirm)
				)
			}
		},
		dismissButton = {
			TextButton(
				onClick = onDismiss
			) {
				Text(
					text = stringResource(id = R.string.cancel)
				)
			}
		},
		onDismiss = onDismiss

	)
}