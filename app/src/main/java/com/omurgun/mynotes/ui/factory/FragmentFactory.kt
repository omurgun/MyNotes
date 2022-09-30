package com.omurgun.mynotes.ui.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.omurgun.mynotes.ui.fragments.*
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            BiometricFragment::class.java.name -> BiometricFragment(viewModelFactory)
            MyNotesFragment::class.java.name -> MyNotesFragment(viewModelFactory)
            AddNoteFragment::class.java.name -> AddNoteFragment(viewModelFactory)
            UpdateNoteFragment::class.java.name -> UpdateNoteFragment(viewModelFactory)
            DeletedNotesFragment::class.java.name -> DeletedNotesFragment(viewModelFactory)
            SettingsFragment::class.java.name -> SettingsFragment(viewModelFactory)

            else -> super.instantiate(classLoader, className)
        }
    }
}