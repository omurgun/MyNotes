package com.omurgun.mynotes.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.domain.useCases.MyNoteUseCase
import com.omurgun.mynotes.ui.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MyNoteViewModel @Inject constructor(
    private val myNoteUseCase: MyNoteUseCase
) : ViewModel() {

    fun getAllNotesFromRoom() : LiveData<ResultData<List<EntityMyNote>>> {
        return myNoteUseCase.getAllNotesUseCase()
    }

    fun getNoteWithIdFromRoom(noteId: Int) : LiveData<ResultData<EntityMyNote?>> {
        return myNoteUseCase.getNoteWithIdUseCase(noteId)
    }

    fun searchByTitleOrDetailFromRoom(search: String) : LiveData<ResultData<List<EntityMyNote>>> {
        return myNoteUseCase.searchByTitleOrDetailUseCase(search)
    }

    fun getDeletedNotesFromRoom() : LiveData<ResultData<List<EntityMyNote>>> {
        return myNoteUseCase.getDeletedNotesUseCase()
    }

    fun insertAllNotesToRoom(notes: List<EntityMyNote>) : LiveData<ResultData<List<Long>>> {
        return myNoteUseCase.insertAllNotesUseCase(notes)
    }

    fun insertNoteToRoom(note: EntityMyNote) : LiveData<ResultData<Long>> {
        return myNoteUseCase.insertNoteUseCase(note)
    }

    fun updateNoteFromRoom(note: EntityMyNote) : LiveData<ResultData<Int>> {
        return myNoteUseCase.updateNoteUseCase(note)
    }

    fun updateDeletedNoteFromRoom(id: Int, isDeleted: Boolean, deletedDate: String?) : LiveData<ResultData<Int>> {
        return myNoteUseCase.updateDeletedNoteUseCase(id,isDeleted,deletedDate)
    }

    fun deleteNoteFromRoom(note: EntityMyNote) : LiveData<ResultData<Int>> {
        return myNoteUseCase.deleteNoteUseCase(note)
    }

    fun deleteAllNotesFromRoom() : LiveData<ResultData<Int>> {
        return myNoteUseCase.deleteAllNotesUseCase()
    }

}
