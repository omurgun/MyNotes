package com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.omurgun.mynotes.data.models.internal.InternalItemSetting
import com.omurgun.mynotes.databinding.ItemSettingBinding

class SettingsViewHolder(private val binding: ItemSettingBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        internalItemSetting: InternalItemSetting
    ) {
        binding.title.text = internalItemSetting.title
    }
}
