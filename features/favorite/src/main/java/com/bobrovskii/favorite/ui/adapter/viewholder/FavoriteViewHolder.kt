package com.bobrovskii.favorite.ui.adapter.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bobrovskii.exchange.domain.entity.CurrencyRate
import com.bobrovskii.favorite.databinding.ItemCurrencyBinding

class FavoriteViewHolder(
	private val binding: ItemCurrencyBinding,
) : RecyclerView.ViewHolder(binding.root) {

	fun bind(
		item: CurrencyRate,
		onItemClicked: (String) -> Unit,
	) {
		with(binding) {
			binding.currencyTitle.text = item.name
			binding.currencyValue.text = item.value.toString()
			binding.ibtnFav.setOnClickListener { onItemClicked(item.name) }
		}
	}

	companion object {

		fun from(parent: ViewGroup): FavoriteViewHolder {
			val layoutInflater = LayoutInflater.from(parent.context)
			val binding = ItemCurrencyBinding.inflate(layoutInflater, parent, false)
			return FavoriteViewHolder(binding)
		}
	}
}