package com.omurgun.mynotes.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.omurgun.mynotes.application.constants.ApplicationConstants.APP_THEME
import com.omurgun.mynotes.databinding.FragmentBottomSheetDialogChangeThemeBinding

class ChangeThemeBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetDialogChangeThemeBinding? = null
    private val binding get() = _binding!!

    private var _theme = MutableLiveData<Int?>().apply {value = null}
    var theme = MutableLiveData<Int?>()


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
        _binding = FragmentBottomSheetDialogChangeThemeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (theme.value == null) {
            when (APP_THEME) {
                1 -> {
                    theme.value = 1
                    binding.radioGroupBox.check(binding.radio0.id)
                }
                2 -> {
                    theme.value = 2
                    binding.radioGroupBox.check(binding.radio1.id)
                }
                3 -> {
                    theme.value = 3
                    binding.radioGroupBox.check(binding.radio2.id)
                }
            }
        }
        else {
            when (theme.value) {
                1 -> {
                    binding.radioGroupBox.check(binding.radio0.id)
                }
                2 -> {
                    binding.radioGroupBox.check(binding.radio1.id)
                }
                3 -> {
                    binding.radioGroupBox.check(binding.radio2.id)
                }
            }
        }

        binding.radioGroupBox.setOnCheckedChangeListener { _, p1 ->
            when (p1) {
                binding.radio0.id -> {
                    _theme.value = 1
                }
                binding.radio1.id -> {
                    _theme.value = 2
                }
                binding.radio2.id -> {
                    _theme.value = 3
                }
            }

        }

        binding.positiveButton.setOnClickListener {
            if (dialog!!.isShowing)
                dialog!!.dismiss()
            theme.value = _theme.value ?: APP_THEME
        }

        _theme.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.positiveButton.isEnabled = it != theme.value
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