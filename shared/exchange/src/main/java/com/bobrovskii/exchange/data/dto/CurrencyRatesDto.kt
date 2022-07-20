package com.bobrovskii.exchange.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyRatesDto(
	val success: Boolean,
	val timestamp: Long,
	val base: String,
	val date: String,
	val rates: Map<String, Double>,
)
