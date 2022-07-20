package com.bobrovskii.exchange.data.repository

import com.bobrovskii.exchange.data.api.ExchangeApi
import com.bobrovskii.exchange.data.datasource.CurrencyNamesLocalDataSource
import com.bobrovskii.exchange.data.datasource.CurrencyNamesRemoteDataSource
import com.bobrovskii.exchange.data.mapper.toCurrencyRate
import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.exchange.domain.entity.CurrencySymbol
import com.bobrovskii.exchange.domain.repository.ExchangeRepository
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
	private val localDataSource: CurrencyNamesLocalDataSource,
	private val remoteDataSource: CurrencyNamesRemoteDataSource,
	private val api: ExchangeApi,
) : ExchangeRepository {

	override suspend fun getCurrencyNames(): List<CurrencySymbol> {
		return localDataSource.getCurrencyNames().ifEmpty {
			val remoteCurrencyNames = remoteDataSource.getCurrencyNames()
			localDataSource.insertCurrencyNames(remoteCurrencyNames)
			localDataSource.getCurrencyNames()
		}
	}

	override suspend fun getLatestRates(base: String): List<CurrencyRate> =
		api.getCurrencyRates(base).toCurrencyRate()

	override suspend fun setFavourite(currency: CurrencySymbol) {
		localDataSource.updateCurrencyNames(currency)
	}
}