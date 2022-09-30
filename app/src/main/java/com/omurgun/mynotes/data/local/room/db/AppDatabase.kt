package com.omurgun.mynotes.data.local.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omurgun.mynotes.data.local.room.dao.MyNoteDao
import com.omurgun.mynotes.data.models.entity.EntityMyNote

@Database(entities = [EntityMyNote::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun myNoteDao(): MyNoteDao
}