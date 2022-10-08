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
import com.omurgun.mynotes.application.constants.ApplicationConstants.LANGUAGE_EN
import com.omurgun.mynotes.application.constants.ApplicationConstants.LANGUAGE_TR
import com.omurgun.mynotes.databinding.FragmentBottomSheetDialogChangeLanguageBinding
import java.util.*

class ChangeLanguageBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetDialogChangeLanguageBinding? = null
    private val binding get() = _binding!!

    private var _language = MutableLiveData<String?>().apply {value = null}
    var language = MutableLiveData<String?>()


    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetDialogChangeLanguageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lang = Locale.getDefault().language
        if (language.value == null) {
            if(lang == "tr") {
                language.value = LANGUAGE_TR
                binding.radioGroupBox.check(binding.radio0.id)
            }
            else {
                language.value = LANGUAGE_EN
                binding.radioGroupBox.check(binding.radio1.id)
            }

        }
        else {
            if(language.value == LANGUAGE_TR) {
                binding.radioGroupBox.check(binding.radio0.id)
            }
            else {
                binding.radioGroupBox.check(binding.radio1.id)
            }
        }


        binding.radioGroupBox.setOnCheckedChangeListener { _, p1 ->
            if (p1 == binding.radio0.id) {
                _language.value = LANGUAGE_TR
            } else if (p1 == binding.radio1.id) {
                _language.value = LANGUAGE_EN
            }

        }

        binding.positiveButton.setOnClickListener {
            if (dialog!!.isShowing)
                dialog!!.dismiss()
            language.value = _language.value
        }

        _language.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.positiveButton.isEnabled = it != language.value
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