package com.omurgun.mynotes.di

import android.content.Context
import androidx.room.Room
import com.omurgun.mynotes.data.local.room.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun injectMovieRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabase::class.java,"MyNoteDB").build()

    @Singleton
    @Provides
    fun injectMyNoteDao(
        database: AppDatabase
    ) = database.myNoteDao()
}



