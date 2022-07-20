package com.bobrovskii.favorite.presentation

sealed interface FavoriteAction {

	data class ShowError(val message: String) : FavoriteAction
}