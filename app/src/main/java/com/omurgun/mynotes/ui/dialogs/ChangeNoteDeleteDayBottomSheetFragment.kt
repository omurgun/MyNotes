package com.omurgun.mynotes.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.omurgun.mynotes.R
import com.omurgun.mynotes.application.constants.ApplicationConstants.NOTE_DELETE_DAY
import com.omurgun.mynotes.databinding.FragmentBottomSheetDialogChangeNoteDeleteDayBinding

class ChangeNoteDeleteDayBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetDialogChangeNoteDeleteDayBinding? = null
    private val binding get() = _binding!!

    private var _noteDeleteDay = MutableLiveData<Int>().apply {value = null}
    var noteDeleteDay = MutableLiveData<Int>()


    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
            //dialog?.setCancelable(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetDialogChangeNoteDeleteDayBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.seekBar.progress = noteDeleteDay.value ?: NOTE_DELETE_DAY
        binding.seekBarValueText.text = getString(R.string.seek_bar_value, noteDeleteDay.value ?: NOTE_DELETE_DAY)

        binding.seekBar.setOnSeekBarChangeListener( object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.seekBarValueText.text = getString(R.string.seek_bar_value, p1)
                _noteDeleteDay.value = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        binding.positiveButton.setOnClickListener {
            if (dialog!!.isShowing)
                dialog!!.dismiss()
            noteDeleteDay.value = _noteDeleteDay.value ?: NOTE_DELETE_DAY
        }

        _noteDeleteDay.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.positiveButton.isEnabled = it != noteDeleteDay.value
            }
            else {
                binding.positiveButton.isEnabled = false
            }
        }



    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}