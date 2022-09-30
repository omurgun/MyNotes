package com.omurgun.mynotes.di

import com.omurgun.mynotes.data.local.room.dao.MyNoteDao
import com.omurgun.mynotes.data.repo.MyNoteRepository
import com.omurgun.mynotes.domain.repoInterfaces.IMyNoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMyNoteRepo(myNoteDao: MyNoteDao) = MyNoteRepository(myNoteDao) as IMyNoteRepository
}