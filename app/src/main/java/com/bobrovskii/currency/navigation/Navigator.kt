package com.bobrovskii.currency.navigation

import androidx.navigation.NavController
import com.bobrovskii.currency.R
import com.bobrovskii.favorite.presentation.FavoriteRouter
import com.bobrovskii.popular.presentation.PopularRouter
import com.bobrovskii.sorting.presentation.SortingRouter

class Navigator : FavoriteRouter, PopularRouter, SortingRouter {

	private var navController: NavController? = null

	fun bind(navController: NavController) {
		this.navController = navController
	}

	fun unbind() {
		navController = null
	}

	override fun routeBack() {
		navController?.popBackStack()
	}

	override fun routeFromFavoriteToSorting() {
		navController?.navigate(R.id.action_favoriteFragment_to_sortingFragment)
	}

	override fun routeFromPopularToSorting() {
		navController?.navigate(R.id.action_popularFragment_to_sortingFragment)
	}
}