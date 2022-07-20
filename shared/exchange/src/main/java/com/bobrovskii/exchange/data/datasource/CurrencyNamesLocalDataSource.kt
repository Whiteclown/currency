package com.bobrovskii.exchange.data.datasource

import com.bobrovskii.exchange.domain.entity.CurrencySymbol

interface CurrencyNamesLocalDataSource {

	suspend fun getCurrencyNames(): List<CurrencySymbol>

	suspend fun insertCurrencyNames(list: List<CurrencySymbol>)

	suspend fun updateCurrencyNames(currencyNames: CurrencySymbol)
}