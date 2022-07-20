package com.bobrovskii.exchange.domain.entity

data class CurrencySymbol(
	val id: Long,
	val name: String,
	var isFavorite: Boolean,
)