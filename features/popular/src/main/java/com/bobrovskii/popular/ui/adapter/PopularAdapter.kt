package com.bobrovskii.popular.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.popular.R
import com.bobrovskii.popular.ui.adapter.viewholder.FavCurrencyViewHolder
import com.bobrovskii.popular.ui.adapter.viewholder.UnFavCurrencyViewHolder
import java.lang.IllegalArgumentException

class PopularAdapter(
	private val onItemUnFavClicked: (String) -> Unit,
	private val onItemFavClicked: (String) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	var currencyRates: List<CurrencyRate>? = null
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun getItemViewType(position: Int): Int =
		if (currencyRates?.get(position)?.isFavourite == true) {
			R.layout.item_currency_fav
		} else {
			R.layout.item_currency_unfav
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
		when (viewType) {
			R.layout.item_currency_fav -> FavCurrencyViewHolder.from(parent)

			R.layout.item_currency_unfav -> UnFavCurrencyViewHolder.from(parent)

			else -> throw IllegalArgumentException("Wrong view holder")
		}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		currencyRates?.let {
			when (holder) {
				is FavCurrencyViewHolder -> {
					holder.bind(
						item = it[position],
						onItemClicked = onItemFavClicked,
					)
				}

				is UnFavCurrencyViewHolder -> {
					holder.bind(
						item = it[position],
						onItemClicked = onItemUnFavClicked,
					)
				}
			}
		}
	}

	override fun getItemCount(): Int = currencyRates?.size ?: 0
}