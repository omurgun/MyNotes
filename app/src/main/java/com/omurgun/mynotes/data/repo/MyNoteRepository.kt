package com.omurgun.mynotes.data.repo

import com.omurgun.mynotes.data.local.room.dao.MyNoteDao
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.domain.repoInterfaces.IMyNoteRepository
import javax.inject.Inject

class MyNoteRepository @Inject constructor(
    private val myNoteDao: MyNoteDao

) : IMyNoteRepository {
    override fun getAllNotesFromRoom(): List<EntityMyNote> {
        return myNoteDao.getAllNotes()
    }

    override fun getNoteWithIdFromRoom(noteId: Int): EntityMyNote? {
        return myNoteDao.getNoteFromId(noteId)
    }

    override fun searchByTitleOrDetailFromRoom(search: String): List<EntityMyNote> {
        return myNoteDao.searchByTitleOrDetail(search)
    }

    override fun getDeletedNotesFromRoom(): List<EntityMyNote> {
        return myNoteDao.getDeletedNotes()
    }

    override suspend fun insertAllNotesToRoom(notes: List<EntityMyNote>): List<Long> {
        return myNoteDao.insertAllNotes(*notes.toTypedArray())
    }

    override suspend fun insertNoteToRoom(note: EntityMyNote): Long {
        return myNoteDao.insertNote(note)
    }

    override suspend fun updateNoteFromRoom(note: EntityMyNote): Int {
        return myNoteDao.updateNote(note)
    }

    override suspend fun updateDeletedNoteFromRoom(
        id: Int,
        isDeleted: Boolean,
        deletedDate: String?
    ): Int {
        return myNoteDao.updateDeletedNote(id,isDeleted,deletedDate)
    }

    override suspend fun deleteNoteFromRoom(note: EntityMyNote): Int {
        return myNoteDao.deleteNote(note)
    }

    override suspend fun deleteAllNotesFromRoom(): Int {
        return myNoteDao.deleteAllNotes()
    }

}