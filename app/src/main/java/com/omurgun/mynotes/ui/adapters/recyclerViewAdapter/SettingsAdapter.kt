package com.omurgun.mynotes.ui.adapters.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.omurgun.mynotes.data.models.internal.InternalItemSetting
import com.omurgun.mynotes.databinding.ItemSettingBinding
import com.omurgun.mynotes.ui.adapters.adapterCallBack.ItemClickListener
import com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.viewHolders.SettingsViewHolder


class SettingsAdapter() : RecyclerView.Adapter<SettingsViewHolder>()
{
    var internalItemSettings: List<InternalItemSetting>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    var listener: ItemClickListener? = null

    private val diffUtil = object : DiffUtil.ItemCallback<InternalItemSetting>() {
        override fun areItemsTheSame(oldItem: InternalItemSetting, newItem: InternalItemSetting): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: InternalItemSetting, newItem: InternalItemSetting): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        return SettingsViewHolder(
            ItemSettingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
    }


    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(internalItemSettings[position])

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(position)
        }

    }

    override fun getItemCount(): Int {
        return internalItemSettings.size
    }




}