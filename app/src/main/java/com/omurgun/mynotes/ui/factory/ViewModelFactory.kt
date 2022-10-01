package com.omurgun.mynotes.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omurgun.mynotes.data.local.dataStore.SettingsDataStore
import com.omurgun.mynotes.domain.useCases.MyNoteUseCase
import com.omurgun.mynotes.ui.viewModel.MyNoteViewModel
import javax.inject.Inject


@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val myNoteUseCase: MyNoteUseCase,
    private val settingsDataStore: SettingsDataStore
    ) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MyNoteViewModel::class.java)) {
            return MyNoteViewModel(myNoteUseCase,settingsDataStore) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

}