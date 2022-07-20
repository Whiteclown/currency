package com.bobrovskii.exchange.data.datasource

import com.bobrovskii.exchange.domain.entity.CurrencySymbol

interface CurrencyNamesRemoteDataSource {

	suspend fun getCurrencyNames(): List<CurrencySymbol>
}