package com.bobrovskii.exchange.domain.usecase

import com.bobrovskii.exchange.domain.entity.SortingPrefs
import com.bobrovskii.exchange.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveSortingPrefsUseCase @Inject constructor(
	private val repository: SettingsRepository,
) {

	suspend operator fun invoke(sortingPref: SortingPrefs) =
		repository.saveSettings(sortingPref)
}