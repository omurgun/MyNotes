package com.omurgun.mynotes.data.local.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.omurgun.mynotes.application.constants.DataStoreConstants.CONSTANT_LANGUAGE

class CustomSharedPreferences {
    companion object {

        internal var sharedPreferences: SharedPreferences? = null

        @Volatile
        private var instance: CustomSharedPreferences? = null
        private val lock = Any()

        operator fun invoke(context: Context): CustomSharedPreferences =
            instance ?: synchronized(lock) {
                instance ?: makeCustomSharedPreferences(context).also {
                    instance = it
                }
            }

        private fun makeCustomSharedPreferences(context: Context): CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }

    }


    fun saveLanguageToSharedPreferences(language: String){
        sharedPreferences?.edit(commit = true) {
            putString(CONSTANT_LANGUAGE, language)

        }
    }


    fun getLanguageFromSharedPreferences() : String? {
        return sharedPreferences?.getString(CONSTANT_LANGUAGE,null)
    }




}