package com.bobrovskii.exchange.data.datasource

import com.bobrovskii.exchange.data.dao.CurrencyNamesDao
import com.bobrovskii.exchange.data.mapper.toDto
import com.bobrovskii.exchange.data.mapper.toEntity
import com.bobrovskii.exchange.domain.entity.CurrencySymbol
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyNamesLocalDataSourceImpl @Inject constructor(
	private val dao: CurrencyNamesDao,
) : CurrencyNamesLocalDataSource {

	override suspend fun getCurrencyNames(): List<CurrencySymbol> =
		withContext(Dispatchers.IO) {
			dao.getCurrencyNames().map {
				it.toEntity()
			}
		}

	override suspend fun insertCurrencyNames(list: List<CurrencySymbol>) =
		withContext(Dispatchers.IO) {
			dao.insertCurrencyNames(list.map { it.toDto() })
		}

	override suspend fun updateCurrencyNames(currencyNames: CurrencySymbol) {
		withContext(Dispatchers.IO) {
			dao.updateCurrencyNames(currencyNames.toDto())
		}
	}
}