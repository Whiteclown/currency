package com.bobrovskii.favorite.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.favorite.ui.adapter.viewholder.FavoriteViewHolder

class FavoriteAdapter(
	private val onItemClicked: (String) -> Unit,
) : RecyclerView.Adapter<FavoriteViewHolder>() {

	var currencyRates: List<CurrencyRate>? = null
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
		FavoriteViewHolder.from(parent)

	override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
		currencyRates?.let {
			holder.bind(
				item = it[position],
				onItemClicked = onItemClicked,
			)
		}
	}

	override fun getItemCount(): Int = currencyRates?.size ?: 0
}