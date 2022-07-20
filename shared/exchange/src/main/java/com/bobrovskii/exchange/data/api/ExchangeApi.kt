package com.bobrovskii.exchange.data.api

import com.bobrovskii.exchange.data.dto.CurrencyNamesDto
import com.bobrovskii.exchange.data.dto.CurrencyRatesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApi {

	@GET("exchangerates_data/symbols")
	suspend fun getCurrencyNames(): CurrencyNamesDto

	@GET("exchangerates_data/latest")
	suspend fun getCurrencyRates(@Query("base") base: String): CurrencyRatesDto
}