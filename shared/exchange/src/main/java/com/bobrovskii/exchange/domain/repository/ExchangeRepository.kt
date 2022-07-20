package com.bobrovskii.exchange.domain.repository

import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.exchange.domain.entity.CurrencySymbol

interface ExchangeRepository {

	suspend fun getCurrencyNames(): List<CurrencySymbol>

	suspend fun getLatestRates(base: String): List<CurrencyRate>

	suspend fun setFavourite(currency: CurrencySymbol)
}