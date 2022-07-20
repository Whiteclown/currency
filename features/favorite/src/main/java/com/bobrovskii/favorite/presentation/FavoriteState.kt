package com.bobrovskii.favorite.presentation

import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.exchange.domain.entity.CurrencySymbol

sealed interface FavoriteState {

	object Loading : FavoriteState

	data class Content(
		val currencyNames: List<CurrencySymbol>,
		val rates: List<CurrencyRate>?,
	) : FavoriteState
}