package com.bobrovskii.exchange.data.mapper

import com.bobrovskii.exchange.data.dto.CurrencyNamesDto
import com.bobrovskii.exchange.data.dto.CurrencyRatesDto
import com.bobrovskii.exchange.data.dto.CurrencySymbolDto
import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.exchange.domain.entity.CurrencySymbol

fun CurrencyNamesDto.toCurrencySymbol(): List<CurrencySymbolDto> {
	val list = mutableListOf<CurrencySymbolDto>()
	this.symbols.forEach {
		list.add(
			CurrencySymbolDto(
				name = it.key,
				isFavorite = false,
			)
		)
	}
	return list
}

fun CurrencyRatesDto.toCurrencyRate(): List<CurrencyRate> {
	val list = mutableListOf<CurrencyRate>()
	this.rates.forEach {
		list.add(
			CurrencyRate(
				name = it.key,
				value = it.value,
			)
		)
	}
	return list
}

fun CurrencySymbolDto.toEntity() =
	CurrencySymbol(
		id = id,
		name = name,
		isFavorite = isFavorite,
	)

fun CurrencySymbol.toDto() =
	CurrencySymbolDto(
		id = id,
		name = name,
		isFavorite = isFavorite,
	)