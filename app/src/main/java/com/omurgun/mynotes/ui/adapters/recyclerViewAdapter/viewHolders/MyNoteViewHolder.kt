package com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.databinding.ItemNoteBinding
import com.omurgun.mynotes.ui.util.Util.getCustomDate


class MyNoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        note: EntityMyNote
    ) {
        binding.title.text = note.title
        binding.detail.text = note.detail
        binding.lastUpdateDate.text = getCustomDate(note.lastVisitDate,binding.root.context) ?: ""
    }
}
