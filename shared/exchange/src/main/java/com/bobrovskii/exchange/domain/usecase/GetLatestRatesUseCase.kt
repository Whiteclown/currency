package com.bobrovskii.exchange.domain.usecase

import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.exchange.domain.repository.ExchangeRepository
import javax.inject.Inject

class GetLatestRatesUseCase @Inject constructor(
	private val repository: ExchangeRepository,
) {

	suspend operator fun invoke(base: String): List<CurrencyRate> =
		repository.getLatestRates(base)
}