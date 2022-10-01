package com.omurgun.mynotes.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.omurgun.mynotes.R
import com.omurgun.mynotes.data.models.internal.InternalItemSetting
import com.omurgun.mynotes.data.models.internal.InternalToolbarItems
import com.omurgun.mynotes.databinding.FragmentSettingsBinding
import com.omurgun.mynotes.databinding.FragmentUpdateNoteBinding
import com.omurgun.mynotes.ui.activities.HomeActivity
import com.omurgun.mynotes.ui.adapters.adapterCallBack.ItemClickListener
import com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.MyNoteAdapter
import com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.SettingsAdapter
import com.omurgun.mynotes.ui.dialogs.ChangeThemeBottomSheetDialogFragment
import com.omurgun.mynotes.ui.factory.ViewModelFactory
import com.omurgun.mynotes.ui.util.Util
import com.omurgun.mynotes.ui.util.makeInVisible
import com.omurgun.mynotes.ui.viewModel.MyNoteViewModel
import javax.inject.Inject

class SettingsFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val myNoteViewModel: MyNoteViewModel by viewModels { viewModelFactory }
    private var toolbarItems: InternalToolbarItems? = null
    private val settingsAdapter: SettingsAdapter = SettingsAdapter()
    private var bottomSheetDialog : ChangeThemeBottomSheetDialogFragment? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        toolbarItems = (requireActivity() as HomeActivity).getToolbarItems()
        (requireActivity() as HomeActivity).supportActionBar?.show()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsRV.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = settingsAdapter
        }
        settingsAdapter.listener = adapterItemClickListener

        settingsAdapter.internalItemSettings = arrayListOf(
            InternalItemSetting(1,Util.getDrawable(R.drawable.ic_baseline_settings_24,requireContext().theme),"Genel Ayarlar"),
            InternalItemSetting(2,Util.getDrawable(R.drawable.ic_baseline_settings_24,requireContext().theme),"Dili Ayarla"),
            InternalItemSetting(3,Util.getDrawable(R.drawable.ic_baseline_settings_24,requireContext().theme),"Temalar")

        )

    }

    private fun showBottomSheet() {
        bottomSheetDialog = ChangeThemeBottomSheetDialogFragment()
        bottomSheetDialog?.show(requireActivity().supportFragmentManager,"ChangeThemeBottomSheetDialog")
    }


    private val adapterItemClickListener = object : ItemClickListener {
        override fun onItemClicked(position: Int) {
            val internalItemSetting = settingsAdapter.internalItemSettings[position]

            when (internalItemSetting.id) {
                1 -> {
                    showBottomSheet()
                }
                2 -> {
                    showBottomSheet()
                }
                3 -> {
                    showBottomSheet()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}