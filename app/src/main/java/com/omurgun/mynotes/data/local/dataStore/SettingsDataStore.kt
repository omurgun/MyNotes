package com.omurgun.mynotes.data.local.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataStore @Inject constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveLanguage(language: String) {
        dataStore.edit {
            it[SettingsPreferencesKeys.LANGUAGE] = language
        }
    }

    val getLanguage: Flow<String?> = dataStore.data
        .map {
            it[SettingsPreferencesKeys.LANGUAGE]
        }


    suspend fun saveNoteDeleteDay(noteDeleteDay: Int) {
        dataStore.edit {
            it[SettingsPreferencesKeys.NOTE_DELETE_DAY] = noteDeleteDay
        }
    }

    val getNoteDeleteDay: Flow<Int?> = dataStore.data
        .map {
            it[SettingsPreferencesKeys.NOTE_DELETE_DAY]
        }


    suspend fun saveTheme(theme: Int) {
        dataStore.edit {
            it[SettingsPreferencesKeys.THEME] = theme
        }
    }

    val getTheme: Flow<Int?> = dataStore.data
        .map {
            it[SettingsPreferencesKeys.THEME]
        }





}