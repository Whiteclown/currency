package com.bobrovskii.popular.ui

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
import com.bobrovskii.popular.R
import com.bobrovskii.popular.databinding.FragmentPopularBinding
import com.bobrovskii.popular.presentation.PopularAction
import com.bobrovskii.popular.presentation.PopularState
import com.bobrovskii.popular.presentation.PopularViewModel
import com.bobrovskii.popular.ui.adapter.PopularAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularFragment : Fragment(R.layout.fragment_popular) {

	private var _binding: FragmentPopularBinding? = null
	private val binding get() = _binding!!

	private val viewModel: PopularViewModel by viewModels()

	private val ratesAdapter = PopularAdapter(
		onItemUnFavClicked = { viewModel.makeFavourite(it) },
		onItemFavClicked = { viewModel.unmakeFavourite(it) },
	)

	private val adapterCurrencyTitle by lazy {
		ArrayAdapter<String>(binding.spinnerCurrencies.context, R.layout.item_currency_title)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentPopularBinding.bind(view)
		initListeners()
		initRVAndSpinners()
		initObservers()
		viewModel.loadCurrencyNames()
	}

	private fun initObservers() {
		viewModel.state.onEach(::render).launchIn(viewModel.viewModelScope)
		viewModel.viewModelScope.launch { viewModel.actions.collect(::handleAction) }
	}

	private fun initRVAndSpinners() {
		binding.spinnerCurrencies.adapter = adapterCurrencyTitle
		binding.rvRates.adapter = ratesAdapter
		binding.rvRates.addItemDecoration(
			DividerItemDecoration(
				context,
				LinearLayoutManager.VERTICAL,
			)
		)
	}

	private fun initListeners() {
		binding.btnSort.setOnClickListener {
			viewModel.routeToSorting()
		}
		binding.spinnerCurrencies.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
				viewModel.loadLatestRates(p0?.getItemAtPosition(p2).toString())
			}

			override fun onNothingSelected(p0: AdapterView<*>?) = Unit
		}
	}

	private fun render(state: PopularState) {
		when (state) {
			is PopularState.Loading -> {}

			is PopularState.Content -> {
				adapterCurrencyTitle.clear()
				adapterCurrencyTitle.addAll(state.currencyNames.map { it.name })
				ratesAdapter.currencyRates = state.rates
			}
		}
	}

	override fun onStart() {
		super.onStart()
		viewModel.refreshFavourites()
	}

	private fun handleAction(action: PopularAction) {
		when (action) {
			is PopularAction.ShowError -> {
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