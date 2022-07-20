package com.bobrovskii.exchange.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bobrovskii.exchange.data.dao.CurrencyNamesDao
import com.bobrovskii.exchange.data.dto.CurrencySymbolDto

@Database(entities = [CurrencySymbolDto::class], version = 1, exportSchema = false)
abstract class ExchangeDataBase : RoomDatabase() {

	abstract fun currencyNamesDao(): CurrencyNamesDao

	companion object {

		const val TABLE_CURRENCY_NAMES = "TableCurrencyNames"
	}
}