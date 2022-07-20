package com.bobrovskii.favorite.ui

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bobrovskii.favorite.R
import com.bobrovskii.favorite.databinding.FragmentFavoriteBinding
import com.bobrovskii.favorite.presentation.FavoriteAction
import com.bobrovskii.favorite.presentation.FavoriteState
import com.bobrovskii.favorite.presentation.FavoriteViewModel
import com.bobrovskii.favorite.ui.adapter.FavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

	private var _binding: FragmentFavoriteBinding? = null
	private val binding: FragmentFavoriteBinding get() = _binding!!

	private val viewModel: FavoriteViewModel by viewModels()

	private val favoriteAdapter = FavoriteAdapter(
		onItemClicked = { viewModel.unmakeFavourite(it) },
	)

	private val adapterCurrencyTitle by lazy {
		ArrayAdapter<String>(binding.spinner.context, R.layout.item_currency_title)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentFavoriteBinding.bind(view)
		initRVAndSpinners()
		initListeners()
		initObservers()
		viewModel.loadCurrencyNames()
	}

	private fun initObservers() {
		viewModel.state.onEach(::render).launchIn(viewModel.viewModelScope)
		viewModel.viewModelScope.launch { viewModel.actions.collect(::handleAction) }
	}

	private fun initRVAndSpinners() {
		binding.spinner.adapter = adapterCurrencyTitle
		binding.recyclerView.addItemDecoration(
			DividerItemDecoration(
				context,
				LinearLayoutManager.VERTICAL,
			)
		)
		binding.recyclerView.adapter = favoriteAdapter
	}

	private fun initListeners() {
		with(binding) {
			button.setOnClickListener {
				viewModel.routeToSorting()
			}
			spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
				override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
					viewModel.loadLatestRates(p0?.getItemAtPosition(p2).toString())
				}

				override fun onNothingSelected(p0: AdapterView<*>?) = Unit
			}
		}
	}

	private fun render(state: FavoriteState) {
		when (state) {
			is FavoriteState.Loading -> {}

			is FavoriteState.Content -> {
				adapterCurrencyTitle.clear()
				adapterCurrencyTitle.addAll(state.currencyNames.map { it.name })
				favoriteAdapter.currencyRates = state.rates
			}
		}
	}

	override fun onStart() {
		super.onStart()
		viewModel.refreshFavourites()
	}

	private fun handleAction(action: FavoriteAction) {
		when (action) {
			is FavoriteAction.ShowError -> {
				val message = SpannableString(action.message)
				message.setSpan(
					ForegroundColorSpan(Color.WHITE),
					0,
					message.length,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
				)
				context?.let {
					AlertDialog
						.Builder(it)
						.setTitle(getString(R.string.error_dialog_title))
						.setMessage(message)
						.setNeutralButton(getString(R.string.error_dialog_neutral_btn)) { _, _ -> }
						.show()
				}
			}
		}
	}
}