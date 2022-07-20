package com.bobrovskii.exchange.domain.repository

import com.bobrovskii.exchange.domain.entity.SortingPrefs

interface SettingsRepository {

	suspend fun getSettings(): SortingPrefs

	suspend fun saveSettings(sortingPref: SortingPrefs)
}