package com.omurgun.mynotes.ui.factory

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface FragmentFactoryEntryPoint {
    fun getFragmentFactory(): FragmentFactory
}