package com.bobrovskii.currency

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.bobrovskii.currency.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val navigator: Navigator,
) : ViewModel() {

	fun bindNavController(navController: NavController) {
		navigator.bind(navController)
	}

	fun unbindNavController() {
		navigator.unbind()
	}
}