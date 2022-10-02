package com.omurgun.mynotes.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.omurgun.mynotes.R
import com.omurgun.mynotes.application.constants.ApplicationConstants.LANGUAGE_EN
import com.omurgun.mynotes.application.constants.ApplicationConstants.LANGUAGE_TR
import com.omurgun.mynotes.data.models.internal.InternalItemSetting
import com.omurgun.mynotes.data.models.internal.InternalToolbarItems
import com.omurgun.mynotes.databinding.FragmentSettingsBinding
import com.omurgun.mynotes.ui.activities.HomeActivity
import com.omurgun.mynotes.ui.adapters.adapterCallBack.ItemClickListener
import com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.SettingsAdapter
import com.omurgun.mynotes.ui.dialogs.ChangeLanguageBottomSheetDialogFragment
import com.omurgun.mynotes.ui.dialogs.ChangeNoteDeleteDayBottomSheetFragment
import com.omurgun.mynotes.ui.dialogs.ChangeThemeBottomSheetDialogFragment
import com.omurgun.mynotes.ui.factory.ViewModelFactory
import com.omurgun.mynotes.ui.util.Util
import com.omurgun.mynotes.ui.viewModel.MyNoteViewModel
import java.util.*
import javax.inject.Inject

class SettingsFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val myNoteViewModel: MyNoteViewModel by viewModels { viewModelFactory }
    private var toolbarItems: InternalToolbarItems? = null
    private val settingsAdapter: SettingsAdapter = SettingsAdapter()


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
            InternalItemSetting(1,Util.getDrawable(R.drawable.ic_baseline_settings_24,requireContext().theme),getString(R.string.notes_delet_time)),
            InternalItemSetting(2,Util.getDrawable(R.drawable.ic_baseline_settings_24,requireContext().theme),getString(R.string.languages)),
            InternalItemSetting(3,Util.getDrawable(R.drawable.ic_baseline_settings_24,requireContext().theme),getString(R.string.app_themes))

        )

    }

    private fun showBottomSheetChangeTheme() {
        val bottomSheetDialog = ChangeThemeBottomSheetDialogFragment()
        val appThemeFromDataStore = myNoteViewModel.getThemeFromDataStore()
        val dataObserver = Observer<Int?> { data ->
            appThemeFromDataStore.removeObservers(this)
            bottomSheetDialog.theme.value = data
            bottomSheetDialog.show(requireActivity().supportFragmentManager,"ChangeThemeBottomSheetDialog")
        }

        appThemeFromDataStore.observe(this,dataObserver)

        bottomSheetDialog.theme.observe(viewLifecycleOwner
        ) { theme ->
            theme?.let {
                myNoteViewModel.saveThemeToDataStore(it)
            }
        }
    }

    private fun showBottomSheetChangeLanguage() {
        val bottomSheetDialog = ChangeLanguageBottomSheetDialogFragment()
        val languageFromDataStore = myNoteViewModel.getLanguageFromDataStore()
        var lang: String? = null
        val dataObserver = Observer<String?> { data ->
            languageFromDataStore.removeObservers(this)

            val localeLang = Locale.getDefault().language
            lang = data
                ?: if(localeLang == "tr") {
                    LANGUAGE_TR

                } else {
                    LANGUAGE_EN

                }

            bottomSheetDialog.language.value = lang
            bottomSheetDialog.show(requireActivity().supportFragmentManager,"ChangeLanguageBottomSheetDialog")
        }

        languageFromDataStore.observe(this,dataObserver)

        bottomSheetDialog.language.observe(viewLifecycleOwner
        ) { language ->
            language?.let {
                if (lang != it) {
                    myNoteViewModel.saveLanguageToDataStore(language)

                    if (language == LANGUAGE_TR) {
                        (requireActivity() as HomeActivity).setLanguage(requireContext(),"tr-TR")
                    }
                    else {
                        (requireActivity() as HomeActivity).setLanguage(requireContext(),"en-US")
                    }
                }


            }
        }
    }

    private fun showBottomSheetChangeNotesDeletedDate() {
        val bottomSheetDialog = ChangeNoteDeleteDayBottomSheetFragment()
        val noteDeleteDayFromDataStore = myNoteViewModel.getNoteDeleteDayFromDataStore()
        val dataObserver = Observer<Int?> { data ->
            noteDeleteDayFromDataStore.removeObservers(this)
            bottomSheetDialog.noteDeleteDay.value = data
            bottomSheetDialog.show(requireActivity().supportFragmentManager,"ChangeNoteDeletedDateBottomSheetDialog")
        }

        noteDeleteDayFromDataStore.observe(this,dataObserver)

        bottomSheetDialog.noteDeleteDay.observe(viewLifecycleOwner
        ) { noteDeleteDay ->
            noteDeleteDay?.let {
                myNoteViewModel.saveNoteDeleteDayToDataStore(it)
            }
        }
    }


    private val adapterItemClickListener = object : ItemClickListener {
        override fun onItemClicked(position: Int) {
            val internalItemSetting = settingsAdapter.internalItemSettings[position]

            when (internalItemSetting.id) {
                1 -> {
                    showBottomSheetChangeNotesDeletedDate()
                }
                2 -> {
                    showBottomSheetChangeLanguage()
                }
                3 -> {
                    showBottomSheetChangeTheme()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}