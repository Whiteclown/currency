package com.bobrovskii.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bobrovskii.exchange.domain.entity.CurrencyRate
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
class FavoriteViewModel @Inject constructor(
	private val router: FavoriteRouter,
	private val getCurrencyNamesUseCase: GetCurrencyNamesUseCase,
	private val getLatestRatesUseCase: GetLatestRatesUseCase,
	private val getSortingPrefsUseCase: GetSortingPrefsUseCase,
	private val setFavouriteUseCase: SetFavouriteUseCase,
) : ViewModel() {

	private val _state = MutableStateFlow<FavoriteState>(FavoriteState.Loading)
	val state = _state.asStateFlow()

	private val _actions: Channel<FavoriteAction> = Channel(Channel.BUFFERED)
	val actions: Flow<FavoriteAction> = _actions.receiveAsFlow()

	fun loadCurrencyNames() {
		if (state.value is FavoriteState.Loading) {
			viewModelScope.launch {
				val currencyNames = getCurrencyNamesUseCase()

				_state.value = FavoriteState.Content(
					currencyNames = currencyNames,
					rates = null,
				)
			}
		}
	}

	fun loadLatestRates(base: String) {
		viewModelScope.launch(Dispatchers.IO) {
			val content = state.value as FavoriteState.Content
			try {
				var rates = getLatestRatesUseCase(base)
				val currencyMap = content.currencyNames.associate { it.name to it.isFavorite }
				rates = rates.filter { currencyMap[it.name] ?: false }
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

				_state.value = FavoriteState.Content(
					currencyNames = content.currencyNames,
					rates = rates,
				)
			} catch (e: Exception) {
				_actions.send(FavoriteAction.ShowError(e.message.toString()))
			}
		}
	}

	fun refreshFavourites() {
		if (state.value is FavoriteState.Content) {
			viewModelScope.launch {
				val content = state.value as FavoriteState.Content
				val currencyNames = getCurrencyNamesUseCase()
				var rates = content.rates
				val currencyMap = content.currencyNames.associate { it.name to it.isFavorite }
				if (rates != null) {
					rates = rates.filter { currencyMap[it.name] ?: false }
				}

				_state.value = FavoriteState.Content(
					currencyNames = currencyNames,
					rates = rates,
				)
			}
		}
	}

	fun unmakeFavourite(currency: String) {
		viewModelScope.launch {
			val content = state.value as FavoriteState.Content
			_state.value = FavoriteState.Loading
			content.currencyNames.find { it.name == currency }?.let {
				it.isFavorite = false
				setFavouriteUseCase(it)
			}

			val rates: List<CurrencyRate> = content.rates?.toMutableList()?.apply {
				find { it.name == currency }?.let {
					it.isFavourite = false
					this.remove(it)
				}
			}?.toList() ?: emptyList()

			_state.value = FavoriteState.Content(
				currencyNames = content.currencyNames,
				rates = rates,
			)
		}
	}

	fun routeToSorting() =
		router.routeFromFavoriteToSorting()
}
