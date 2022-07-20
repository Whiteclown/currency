package com.bobrovskii.currency

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bobrovskii.currency.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private val viewModel: MainViewModel by viewModels()

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
	}

	override fun onStart() {
		super.onStart()
		binding.bottomNavigationView.setupWithNavController(findNavController(R.id.host_global))
	}

	override fun onResume() {
		super.onResume()
		viewModel.bindNavController(findNavController(R.id.host_global))
	}

	override fun onPause() {
		super.onPause()
		viewModel.unbindNavController()
	}
}