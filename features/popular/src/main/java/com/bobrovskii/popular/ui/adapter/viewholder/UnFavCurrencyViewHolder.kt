package com.bobrovskii.popular.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.popular.databinding.ItemCurrencyUnfavBinding

class UnFavCurrencyViewHolder(
	private val binding: ItemCurrencyUnfavBinding,
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

		fun from(parent: ViewGroup): UnFavCurrencyViewHolder {
			val layoutInflater = LayoutInflater.from(parent.context)
			val binding = ItemCurrencyUnfavBinding.inflate(layoutInflater, parent, false)
			return UnFavCurrencyViewHolder(binding)
		}
	}
}