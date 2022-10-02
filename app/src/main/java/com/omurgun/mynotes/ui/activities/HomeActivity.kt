package com.omurgun.mynotes.ui.activities

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.omurgun.mynotes.R
import com.omurgun.mynotes.data.local.dataStore.SettingsDataStore
import com.omurgun.mynotes.data.local.sharedPreferences.CustomSharedPreferences
import com.omurgun.mynotes.data.models.internal.InternalToolbarItems
import com.omurgun.mynotes.databinding.ActivityHomeBinding
import com.omurgun.mynotes.ui.dialogs.CustomDialogManager
import com.omurgun.mynotes.ui.factory.FragmentFactoryEntryPoint
import com.zeugmasolutions.localehelper.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var customDialogManager : CustomDialogManager
    private lateinit var customSharedPreferences : CustomSharedPreferences


    @Inject
    lateinit var settingsDataStore : SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        val entryPoint = EntryPointAccessors.fromActivity(
            this,
            FragmentFactoryEntryPoint::class.java
        )
        supportFragmentManager.fragmentFactory = entryPoint.getFragmentFactory()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.hide()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        customDialogManager = CustomDialogManager()


        /*settingsDataStore.getLanguage.asLiveData(Dispatchers.IO).observe(this@HomeActivity) {
            if (it == null) {
                val lang = Locale.getDefault().language

                if(lang == "tr") {
                    //super.attachBaseContext(updateResourcesLegacy(newBase, "tr"))
                }
                else {
                    //super.attachBaseContext(updateResourcesLegacy(newBase, "en"))
                }
            }
            else {
                //super.attachBaseContext(updateResourcesLegacy(newBase, it))
            }
        }*/


    }

    override fun onPause() {
        super.onPause()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.popBackStack(R.id.biometricFragment, false)

    }



    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun getToolbarItems() : InternalToolbarItems {
        return InternalToolbarItems(
            binding.endButton,
            binding.secondEndButton,
            binding.title,
            binding.trashItemCount,
            binding.trashItemCountBackgroundView
        )
    }

    fun getCustomDialogManager() : CustomDialogManager {
        return customDialogManager
    }


    override fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        val context = super.createConfigurationContext(overrideConfiguration)
        return LocaleHelper.onAttach(context)
    }

    fun setLanguage(context:Context,lang: String) : Context{
        customSharedPreferences.saveLanguageToSharedPreferences(lang)

        val metrics : DisplayMetrics = applicationContext.resources.displayMetrics
        val configuration : Configuration = applicationContext.resources.configuration
        configuration.setLocale(Locale(lang.split("-")[0]))
        applicationContext.resources.updateConfiguration(configuration, metrics)
        onConfigurationChanged(configuration)

        return context

    }

    override fun attachBaseContext(newBase: Context) {
        customSharedPreferences = CustomSharedPreferences(newBase.applicationContext)
        var localeLanguage = Locale.getDefault().language

        localeLanguage = customSharedPreferences.getLanguageFromSharedPreferences()
            ?: if (localeLanguage == "tr") {
                "tr-TR"
            } else {
                "en-US"
            }

        super.attachBaseContext(updateResourcesLegacy(newBase, localeLanguage))
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        if (language != "") {
            val locale = Locale(language.split("-")[0])
            Locale.setDefault(locale)
            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        else {

            val lang = Locale.getDefault().displayLanguage
            if (lang == "Türkçe")
            {
                customSharedPreferences.saveLanguageToSharedPreferences("tr-TR")
                val locale = Locale("tr")
                Locale.setDefault(locale)
                val resources = context.resources
                val configuration = resources.configuration
                configuration.setLocale(locale)
                resources.updateConfiguration(configuration, resources.displayMetrics)

            }
            else
            {
                customSharedPreferences.saveLanguageToSharedPreferences("en-US")
                val locale = Locale("en")
                Locale.setDefault(locale)
                val resources = context.resources
                val configuration = resources.configuration
                configuration.setLocale(locale)
                resources.updateConfiguration(configuration, resources.displayMetrics)
            }

        }
        return context
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        println("change language 2 ")

        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            launch(Dispatchers.Main) {
                val intent = Intent(this@HomeActivity,HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }

        }

    }



}