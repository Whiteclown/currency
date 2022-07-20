package com.bobrovskii.sorting.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrovskii.exchange.domain.entity.SortingPrefs
import com.bobrovskii.exchange.domain.usecase.GetSortingPrefsUseCase
import com.bobrovskii.exchange.domain.usecase.SaveSortingPrefsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortingViewModel @Inject constructor(
	private val getSortingPrefsUseCase: GetSortingPrefsUseCase,
	private val saveSortingPrefsUseCase: SaveSortingPrefsUseCase,
	private val router: SortingRouter,
) : ViewModel() {

	private val _state = MutableStateFlow<SortingState>(SortingState.Loading)
	val state = _state.asStateFlow()

	fun loadSortPref() {
		if (state.value is SortingState.Loading) {
			viewModelScope.launch {
				val sortingPrefs = getSortingPrefsUseCase()

				_state.value = SortingState.Content(
					sortingPrefs = sortingPrefs,
				)
			}
		}
	}

	fun saveSortPref(pref: SortingPrefs) {
		viewModelScope.launch {
			saveSortingPrefsUseCase(pref)
			routeBack()
		}
	}

	private fun routeBack() =
		router.routeBack()
}