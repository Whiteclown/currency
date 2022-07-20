package com.bobrovskii.exchange.domain.entity

data class CurrencyRate(
	val name: String,
	val value: Double,
	var isFavourite: Boolean = false,
)