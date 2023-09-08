package com.example.myhome.feature_home.presentation.my_home_screen.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myhome.R

@Composable
fun ErrorMessage(
	onRetry: () -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Image(
			painter = painterResource(id = R.drawable.error),
			contentDescription = "Error icon",
			modifier = Modifier
				.size(64.dp)
		)
		Spacer(modifier = Modifier.height(8.dp))
		Text(
			text = stringResource(id = R.string.something_went_wrong),
			color = MaterialTheme.colors.error,
			modifier = Modifier
				.align(Alignment.CenterHorizontally)
		)
		Spacer(modifier = Modifier.height(16.dp))
		Button(
			onClick = onRetry
		) {
			Text(
				text = stringResource(id = R.string.try_again)
			)
		}
	}
}