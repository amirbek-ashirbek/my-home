package com.example.myhome.feature_home.presentation.my_home_screen

import androidx.annotation.StringRes
import com.example.myhome.R

sealed class MyHomeTab(
	@StringRes val title: Int
) {

	object Cameras : MyHomeTab(
		title = R.string.cameras
	)

	object Doors : MyHomeTab(
		title = R.string.doors
	)

}
