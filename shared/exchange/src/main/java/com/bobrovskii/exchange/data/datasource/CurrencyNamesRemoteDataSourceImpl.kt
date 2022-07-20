package com.bobrovskii.exchange.data.datasource

import com.bobrovskii.exchange.data.api.ExchangeApi
import com.bobrovskii.exchange.data.mapper.toCurrencySymbol
import com.bobrovskii.exchange.data.mapper.toEntity
import com.bobrovskii.exchange.domain.entity.CurrencySymbol
import javax.inject.Inject

class CurrencyNamesRemoteDataSourceImpl @Inject constructor(
	private val api: ExchangeApi,
) : CurrencyNamesRemoteDataSource {

	override suspend fun getCurrencyNames(): List<CurrencySymbol> =
		api.getCurrencyNames().toCurrencySymbol().map { it.toEntity() }
}