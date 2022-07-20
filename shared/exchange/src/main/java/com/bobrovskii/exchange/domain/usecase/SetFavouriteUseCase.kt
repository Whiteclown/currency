package com.bobrovskii.exchange.domain.usecase

import com.bobrovskii.exchange.domain.entity.CurrencySymbol
import com.bobrovskii.exchange.domain.repository.ExchangeRepository
import javax.inject.Inject

class SetFavouriteUseCase @Inject constructor(
	private val repository: ExchangeRepository,
) {

	suspend operator fun invoke(currency: CurrencySymbol) =
		repository.setFavourite(currency)
}