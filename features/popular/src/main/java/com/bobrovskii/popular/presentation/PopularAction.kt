package com.bobrovskii.popular.presentation

sealed interface PopularAction {

	data class ShowError(val message: String) : PopularAction
}