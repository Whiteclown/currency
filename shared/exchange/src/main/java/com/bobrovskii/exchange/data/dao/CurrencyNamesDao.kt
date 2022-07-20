package com.bobrovskii.exchange.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bobrovskii.exchange.data.database.ExchangeDataBase
import com.bobrovskii.exchange.data.dto.CurrencyNamesDto
import com.bobrovskii.exchange.data.dto.CurrencySymbolDto

@Dao
interface CurrencyNamesDao {

	@Query("SELECT * FROM ${ExchangeDataBase.TABLE_CURRENCY_NAMES}")
	suspend fun getCurrencyNames(): List<CurrencySymbolDto>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCurrencyNames(list: List<CurrencySymbolDto>)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	suspend fun updateCurrencyNames(currencySymbol: CurrencySymbolDto)
}