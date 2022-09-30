package com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.databinding.ItemNoteBinding
import com.omurgun.mynotes.ui.util.Util
import java.util.*

class DeletedNoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        note: EntityMyNote
    ) {
        binding.title.text = note.title
        binding.detail.text = note.detail
        binding.lastUpdateDate.text = getVisitDate(note.deletedDate ?: "") ?: ""
    }

    private fun getVisitDate(date: String) : String? {
        val today = Util.smashDate(Util.dateTimeFormatter(Date()))
        val currentDate = Util.smashDate(date)

        if (today != null && currentDate != null) {
            if (today.year == currentDate.year) {
                return if (today.month == currentDate.month) {
                    if (today.day == currentDate.day) {
                        "${currentDate.hour} ${currentDate.minute}"
                    } else if (Integer.parseInt(today.day) == Integer.parseInt(currentDate.day) + 1) {
                        "Yesterday ${currentDate.hour} ${currentDate.minute}"
                    } else {
                        "${currentDate.day} ${Util.getMonthString(Integer.parseInt(currentDate.month)) ?: ""}"
                    }

                } else {
                    "${currentDate.day}  ${Util.getMonthString(Integer.parseInt(currentDate.month)) ?: ""}"
                }
            }
            else {
                return "${currentDate.day} ${currentDate.month} ${currentDate.year}"
            }
        }
        return null
    }


}
