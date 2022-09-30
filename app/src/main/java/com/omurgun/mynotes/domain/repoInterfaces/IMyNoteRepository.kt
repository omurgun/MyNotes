package com.omurgun.mynotes.domain.repoInterfaces

import com.omurgun.mynotes.data.models.entity.EntityMyNote

interface IMyNoteRepository {
    fun getAllNotesFromRoom(): List<EntityMyNote>
    fun getNoteWithIdFromRoom(noteId: Int): EntityMyNote?
    fun searchByTitleOrDetailFromRoom(search: String): List<EntityMyNote>
    fun getDeletedNotesFromRoom(): List<EntityMyNote>
    suspend fun insertAllNotesToRoom(notes: List<EntityMyNote>): List<Long>
    suspend fun insertNoteToRoom(note: EntityMyNote): Long
    suspend fun updateNoteFromRoom(note: EntityMyNote): Int
    suspend fun updateDeletedNoteFromRoom(id:Int, isDeleted: Boolean, deletedDate: String?): Int
    suspend fun deleteNoteFromRoom(note: EntityMyNote): Int
    suspend fun deleteAllNotesFromRoom(): Int


}