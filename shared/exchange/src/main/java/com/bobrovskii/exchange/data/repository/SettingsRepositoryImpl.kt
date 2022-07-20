package com.bobrovskii.exchange.data.repository

import android.content.Context
import com.bobrovskii.exchange.domain.entity.SortingPrefs
import com.bobrovskii.exchange.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
	@ApplicationContext private val context: Context,
) : SettingsRepository {

	private val settingsPreferences by lazy {
		context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
	}

	override suspend fun getSettings(): SortingPrefs =
		try {
			settingsPreferences.getString(SORTING_VALUE, null)?.let {
				enumValueOf<SortingPrefs>(it)
			} ?: SortingPrefs.ALPHABET_INC
		} catch (e: IllegalArgumentException) {
			SortingPrefs.ALPHABET_INC
		}

	override suspend fun saveSettings(sortingPref: SortingPrefs) {
		settingsPreferences
			.edit()
			.putString(SORTING_VALUE, sortingPref.name)
			.apply()
	}

	private companion object {

		const val SETTINGS_PREFERENCES = "settingsPreferences"
		const val SORTING_VALUE = "sortingValue"
	}
}