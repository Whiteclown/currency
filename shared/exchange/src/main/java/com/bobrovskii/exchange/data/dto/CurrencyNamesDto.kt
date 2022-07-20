package com.bobrovskii.exchange.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyNamesDto(
	val id: Long = 0,
	val success: Boolean,
	val symbols: Map<String, String>,
)