package com.example.myhome.navigation

interface MyHomeDestination {
	val route: String
}

object MyHomeScreen : MyHomeDestination {
	override val route = "my_home"
}