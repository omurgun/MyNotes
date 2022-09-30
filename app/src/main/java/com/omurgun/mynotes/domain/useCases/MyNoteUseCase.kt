package com.omurgun.mynotes.domain.useCases

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.domain.repoInterfaces.IMyNoteRepository
import com.omurgun.mynotes.ui.util.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject


class MyNoteUseCase @Inject constructor(private val myNoteRepository: IMyNoteRepository) {

    fun getAllNotesUseCase() : LiveData<ResultData<List<EntityMyNote>>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.getAllNotesFromRoom()
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)


    fun getNoteWithIdUseCase(noteId: Int) : LiveData<ResultData<EntityMyNote?>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.getNoteWithIdFromRoom(noteId)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)

    fun searchByTitleOrDetailUseCase(search: String) :LiveData<ResultData<List<EntityMyNote>>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.searchByTitleOrDetailFromRoom(search)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)

    fun getDeletedNotesUseCase() : LiveData<ResultData<List<EntityMyNote>>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.getDeletedNotesFromRoom()
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)

    fun insertAllNotesUseCase(notes: List<EntityMyNote>) : LiveData<ResultData<List<Long>>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.insertAllNotesToRoom(notes)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)

    fun insertNoteUseCase(note: EntityMyNote) : LiveData<ResultData<Long>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.insertNoteToRoom(note)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)

    fun updateNoteUseCase(note: EntityMyNote) : LiveData<ResultData<Int>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.updateNoteFromRoom(note)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)

    fun updateDeletedNoteUseCase(id: Int, isDeleted: Boolean, deletedDate: String?) : LiveData<ResultData<Int>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.updateDeletedNoteFromRoom(id,isDeleted,deletedDate)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)

    fun deleteNoteUseCase(note: EntityMyNote) : LiveData<ResultData<Int>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.deleteNoteFromRoom(note)
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)


    fun deleteAllNotesUseCase() : LiveData<ResultData<Int>> = flow {
        try {
            emit(ResultData.Loading())
            val movie = myNoteRepository.deleteAllNotesFromRoom()
            emit(ResultData.Success(movie))
        } catch (e: HttpException) {
            emit(ResultData.Exception(message = e.localizedMessage ?: "Error!"))
        } catch (e: IOError) {
            emit(ResultData.Exception(message = "Could not reach internet"))
        }
    }.asLiveData(Dispatchers.IO)



}