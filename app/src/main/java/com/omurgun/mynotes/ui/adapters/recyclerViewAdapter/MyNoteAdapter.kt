package com.omurgun.mynotes.ui.adapters.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.databinding.ItemNoteBinding
import com.omurgun.mynotes.ui.adapters.adapterCallBack.ItemClickListener
import com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.viewHolders.MyNoteViewHolder

class MyNoteAdapter() : RecyclerView.Adapter<MyNoteViewHolder>()
{
    var myNotes: List<EntityMyNote>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    var listener: ItemClickListener? = null

    private val diffUtil = object : DiffUtil.ItemCallback<EntityMyNote>() {
        override fun areItemsTheSame(oldItem: EntityMyNote, newItem: EntityMyNote): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EntityMyNote, newItem: EntityMyNote): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNoteViewHolder {
        return MyNoteViewHolder(
            ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }


    override fun onBindViewHolder(holder: MyNoteViewHolder, position: Int) {
        holder.bind(myNotes[position])

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(position)
        }

    }

    override fun getItemCount(): Int {
        return myNotes.size
    }




}