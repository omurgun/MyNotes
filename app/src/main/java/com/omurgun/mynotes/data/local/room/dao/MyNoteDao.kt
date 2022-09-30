package com.omurgun.mynotes.data.local.room.dao


import androidx.room.*
import com.omurgun.mynotes.data.models.entity.EntityMyNote

@Dao
interface MyNoteDao {

    @Query("SELECT * FROM myNotes WHERE isDeleted == 0 ORDER BY lastVisitDate DESC")
    fun getAllNotes() : List<EntityMyNote>

    @Query("SELECT * FROM myNotes WHERE id = :id")
    fun getNoteFromId(id : Int) : EntityMyNote?

    @Query("SELECT * FROM myNotes WHERE (title LIKE :search or detail LIKE :search) and isDeleted == 0 ORDER BY lastVisitDate DESC")
    fun searchByTitleOrDetail(search: String) : List<EntityMyNote>

    @Query("SELECT * FROM myNotes WHERE isDeleted == 1 ORDER BY deletedDate DESC")
    fun getDeletedNotes() : List<EntityMyNote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllNotes(vararg notes: EntityMyNote) : List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : EntityMyNote) : Long

    @Update
    suspend fun updateNote(note: EntityMyNote) : Int

    @Query("UPDATE myNotes " +
            "SET isDeleted = :isDeleted , deletedDate = :deletedDate " +
            "WHERE id = :id")
    suspend fun updateDeletedNote(id:Int, isDeleted: Boolean, deletedDate: String?) : Int

    @Query("DELETE FROM myNotes where isDeleted == 1")
    suspend fun deleteAllNotes() : Int

    @Delete
    suspend fun deleteNote(note: EntityMyNote) : Int







}