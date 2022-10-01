package com.omurgun.mynotes.data.local.dataStore

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.omurgun.mynotes.application.constants.DataStoreConstants.CONSTANT_LANGUAGE
import com.omurgun.mynotes.application.constants.DataStoreConstants.CONSTANT_NOTE_DELETE_DAY
import com.omurgun.mynotes.application.constants.DataStoreConstants.CONSTANT_THEME

object SettingsPreferencesKeys {
    val LANGUAGE = stringPreferencesKey(CONSTANT_LANGUAGE)
    val NOTE_DELETE_DAY = intPreferencesKey(CONSTANT_NOTE_DELETE_DAY)
    val THEME = intPreferencesKey(CONSTANT_THEME)

}