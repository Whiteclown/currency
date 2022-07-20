package com.bobrovskii.sorting.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.bobrovskii.exchange.domain.entity.SortingPrefs
import com.bobrovskii.sorting.R
import com.bobrovskii.sorting.databinding.FragmentSortingBinding
import com.bobrovskii.sorting.presentation.SortingState
import com.bobrovskii.sorting.presentation.SortingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SortingFragment : Fragment(R.layout.fragment_sorting) {

	private var _binding: FragmentSortingBinding? = null
	private val binding get() = _binding!!

	private val viewModel: SortingViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentSortingBinding.bind(view)
		initListeners()
		viewModel.state.onEach(::render).launchIn(viewModel.viewModelScope)
		viewModel.loadSortPref()
	}

	private fun initListeners() {
		with(binding) {
			rbtnAlbInc.setOnClickListener { viewModel.saveSortPref(SortingPrefs.ALPHABET_INC) }
			rbtnAlbDec.setOnClickListener { viewModel.saveSortPref(SortingPrefs.ALPHABET_DEC) }
			rbtnValInc.setOnClickListener { viewModel.saveSortPref(SortingPrefs.VALUE_INC) }
			rbtnValDec.setOnClickListener { viewModel.saveSortPref(SortingPrefs.VALUE_DEC) }
		}
	}

	private fun render(state: SortingState) {
		when (state) {
			is SortingState.Loading -> {}

			is SortingState.Content -> {
				when (state.sortingPrefs) {
					SortingPrefs.ALPHABET_INC -> binding.rgSort.check(binding.rbtnAlbInc.id)

					SortingPrefs.ALPHABET_DEC -> binding.rgSort.check(binding.rbtnAlbDec.id)

					SortingPrefs.VALUE_INC -> binding.rgSort.check(binding.rbtnValInc.id)

					SortingPrefs.VALUE_DEC -> binding.rgSort.check(binding.rbtnValDec.id)
				}
			}
		}
	}
}