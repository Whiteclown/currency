package com.bobrovskii.popular.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrovskii.exchange.domain.entity.SortingPrefs
import com.bobrovskii.exchange.domain.usecase.GetCurrencyNamesUseCase
import com.bobrovskii.exchange.domain.usecase.GetLatestRatesUseCase
import com.bobrovskii.exchange.domain.usecase.GetSortingPrefsUseCase
import com.bobrovskii.exchange.domain.usecase.SetFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
	private val router: PopularRouter,
	private val getCurrencyNamesUseCase: GetCurrencyNamesUseCase,
	private val getLatestRatesUseCase: GetLatestRatesUseCase,
	private val getSortingPrefsUseCase: GetSortingPrefsUseCase,
	private val setFavouriteUseCase: SetFavouriteUseCase,
) : ViewModel() {

	private val _state = MutableStateFlow<PopularState>(PopularState.Loading)
	val state get() = _state.asStateFlow()

	private val _actions: Channel<PopularAction> = Channel(Channel.BUFFERED)
	val actions: Flow<PopularAction> = _actions.receiveAsFlow()

	fun loadCurrencyNames() {
		if (state.value is PopularState.Loading) {
			viewModelScope.launch {
				val currencyNames = getCurrencyNamesUseCase()

				_state.value = PopularState.Content(
					currencyNames = currencyNames,
					rates = null,
				)
			}
		}
	}

	fun loadLatestRates(base: String) {
		if (state.value is PopularState.Content) {
			val content = state.value as PopularState.Content
			viewModelScope.launch(Dispatchers.IO) {
				try {
					val currencyNamesMap = content.currencyNames.associate { it.name to it.isFavorite }
					var rates = getLatestRatesUseCase(base)
					rates.forEach { it.isFavourite = currencyNamesMap[it.name] == true }
					val sortingPrefs = getSortingPrefsUseCase()
					when (sortingPrefs) {
						SortingPrefs.ALPHABET_INC -> {
							rates = rates.toMutableList().apply { this.sortBy { it.name } }
						}

						SortingPrefs.ALPHABET_DEC -> {
							rates = rates.toMutableList().apply { this.sortByDescending { it.name } }
						}

						SortingPrefs.VALUE_INC    -> {
							rates = rates.toMutableList().apply { this.sortBy { it.value } }
						}

						SortingPrefs.VALUE_DEC    -> {
							rates = rates.toMutableList().apply { this.sortByDescending { it.value } }
						}
					}

					_state.value = PopularState.Content(
						currencyNames = content.currencyNames,
						rates = rates,
					)
				} catch (e: Exception) {
					_actions.send(PopularAction.ShowError(e.message.toString()))
				}
			}
		}
	}

	fun makeFavourite(currency: String) {
		val content = state.value as PopularState.Content
		_state.value = PopularState.Loading
		viewModelScope.launch {
			content.currencyNames.find { it.name == currency }?.let {
				it.isFavorite = true
				setFavouriteUseCase(it)
			}

			content.rates?.find { it.name == currency }?.let {
				it.isFavourite = true
			}

			_state.value = PopularState.Content(
				currencyNames = content.currencyNames,
				rates = content.rates,
			)
		}
	}

	fun unmakeFavourite(currency: String) {
		val content = state.value as PopularState.Content
		_state.value = PopularState.Loading
		viewModelScope.launch {
			content.currencyNames.find { it.name == currency }?.let {
				it.isFavorite = false
				setFavouriteUseCase(it)
			}

			content.rates?.find { it.name == currency }?.let {
				it.isFavourite = false
			}

			_state.value = PopularState.Content(
				currencyNames = content.currencyNames,
				rates = content.rates,
			)
		}
	}

	fun refreshFavourites() {
		if (state.value is PopularState.Content) {
			viewModelScope.launch {
				val content = state.value as PopularState.Content
				_state.value = PopularState.Loading
				val currencyNames = getCurrencyNamesUseCase()
				val rates = content.rates
				val currencyNamesMap = currencyNames.associate { it.name to it.isFavorite }
				rates?.forEach {
					it.isFavourite = currencyNamesMap[it.name] == true
				}

				_state.value = PopularState.Content(
					currencyNames = currencyNames,
					rates = rates,
				)
			}
		}
	}

	fun routeToSorting() =
		router.routeFromPopularToSorting()
}