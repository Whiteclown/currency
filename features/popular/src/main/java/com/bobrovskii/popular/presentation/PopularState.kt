package com.bobrovskii.popular.presentation

import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.exchange.domain.entity.CurrencySymbol

sealed interface PopularState {

	object Loading : PopularState

	data class Content(
		val currencyNames: List<CurrencySymbol>,
		val rates: List<CurrencyRate>?,
	) : PopularState
}