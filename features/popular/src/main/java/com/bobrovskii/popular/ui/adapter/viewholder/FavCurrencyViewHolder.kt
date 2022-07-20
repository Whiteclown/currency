package com.bobrovskii.popular.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.popular.databinding.ItemCurrencyFavBinding

class FavCurrencyViewHolder(
	private val binding: ItemCurrencyFavBinding,
) : RecyclerView.ViewHolder(binding.root) {

	fun bind(
		item: CurrencyRate,
		onItemClicked: (String) -> Unit,
	) {
		binding.currencyTitle.text = item.name
		binding.currencyValue.text = item.value.toString()
		binding.ibtnFav.setOnClickListener { onItemClicked(item.name) }
	}

	companion object {

		fun from(parent: ViewGroup): FavCurrencyViewHolder {
			val layoutInflater = LayoutInflater.from(parent.context)
			val binding = ItemCurrencyFavBinding.inflate(layoutInflater, parent, false)
			return FavCurrencyViewHolder(binding)
		}
	}
}