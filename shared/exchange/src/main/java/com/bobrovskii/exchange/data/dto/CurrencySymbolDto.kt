package com.bobrovskii.exchange.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bobrovskii.exchange.data.database.ExchangeDataBase

@Entity(tableName = ExchangeDataBase.TABLE_CURRENCY_NAMES)
data class CurrencySymbolDto(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0,
	val name: String,
	val isFavorite: Boolean,
)