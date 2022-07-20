package com.bobrovskii.sorting.presentation

import com.bobrovskii.exchange.domain.entity.SortingPrefs

sealed interface SortingState {

	object Loading : SortingState

	data class Content(
		val sortingPrefs: SortingPrefs,
	) : SortingState
}