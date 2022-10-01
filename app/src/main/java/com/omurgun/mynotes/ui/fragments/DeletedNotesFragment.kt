package com.omurgun.mynotes.ui.fragments

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.omurgun.mynotes.application.constants.ApplicationConstants.NOTE_DELETE_DAY
import com.omurgun.mynotes.application.constants.ApplicationConstants.ONE_DAY_TIME
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.data.models.internal.InternalDate
import com.omurgun.mynotes.data.models.internal.InternalToolbarItems
import com.omurgun.mynotes.databinding.FragmentDeletedNotesBinding
import com.omurgun.mynotes.ui.activities.HomeActivity
import com.omurgun.mynotes.ui.adapters.adapterCallBack.ItemClickListener
import com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.DeletedNoteAdapter
import com.omurgun.mynotes.ui.factory.ViewModelFactory
import com.omurgun.mynotes.ui.util.ResultData
import com.omurgun.mynotes.ui.util.Util
import com.omurgun.mynotes.ui.util.makeVisible
import com.omurgun.mynotes.ui.viewModel.MyNoteViewModel
import java.util.*
import javax.inject.Inject


class DeletedNotesFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : Fragment() {
    private var _binding: FragmentDeletedNotesBinding? = null
    private val binding get() = _binding!!
    private val myNoteViewModel: MyNoteViewModel by viewModels { viewModelFactory }
    private val deletedNoteAdapter: DeletedNoteAdapter = DeletedNoteAdapter()
    private var toolbarItems: InternalToolbarItems? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeletedNotesBinding.inflate(inflater, container, false)
        toolbarItems = (requireActivity() as HomeActivity).getToolbarItems()
        (requireActivity() as HomeActivity).supportActionBar?.show()
        toolbarItems?.endButton?.setImageDrawable(
            Util.getDrawable(
                com.omurgun.mynotes.R.drawable.ic_baseline_clear_24,
                requireContext().theme
            )
        )
        toolbarItems?.endButton?.makeVisible()
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDeletedNotesFromRoom()

        binding.myDeletedNotesRv.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = deletedNoteAdapter
        }
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.myDeletedNotesRv)
        deletedNoteAdapter.listener = adapterItemClickListener

        toolbarItems?.endButton?.setOnClickListener {
            @SuppressLint("NotifyDataSetChanged")
            val areYouSureDialog = (requireActivity() as HomeActivity).getCustomDialogManager()
                .showAreYouSureDialog(
                    requireContext(),
                    "Are you sure to delete all notes?",
                    "Yes",
                    "No",
                    positiveButtonClicked = {
                        deleteAllNotesFromRoom()
                    })
            areYouSureDialog.show()

        }





    }

    private val adapterItemClickListener = object : ItemClickListener {

        override fun onItemClicked(position: Int) {
            @SuppressLint("NotifyDataSetChanged")
            val areYouSureDialog = (requireActivity() as HomeActivity).getCustomDialogManager()
                .showAreYouSureDialog(
                    requireContext(),
                    "Are you sure about restoring this note?",
                    "Yes",
                    "No",
                    positiveButtonClicked = {
                        val note = deletedNoteAdapter.deletedNotes[position]
                        note.isDeleted = false
                        note.deletedDate = null
                        updateDeletedNoteFromRoom(note)
                    }, negativeButtonClicked = {
                        val newList = deletedNoteAdapter.deletedNotes.toMutableList()
                        deletedNoteAdapter.deletedNotes = newList
                        deletedNoteAdapter.notifyDataSetChanged()
                    })
            areYouSureDialog.show()
        }


    }

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedNote = deletedNoteAdapter.deletedNotes[layoutPosition]
            selectedNote.isDeleted = true
            selectedNote.deletedDate = Util.dateTimeFormatter(Date())

            val areYouSureDialog = (requireActivity() as HomeActivity).getCustomDialogManager()
                .showAreYouSureDialog(
                    requireContext(),
                    "Are you sure about deleting this note?",
                    "Yes",
                    "No",
                    positiveButtonClicked = {
                        deleteNoteFromRoom(selectedNote)
                    }, negativeButtonClicked = {
                        val newList = deletedNoteAdapter.deletedNotes.toMutableList()
                        deletedNoteAdapter.deletedNotes = newList
                        deletedNoteAdapter.notifyDataSetChanged()
                    })
            areYouSureDialog.show()
        }

    }


    private fun getDeletedNotesFromRoom() {
        val data = myNoteViewModel.getDeletedNotesFromRoom()

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    //binding.movieDetailContainer.makeInVisible()
                    //binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("data : ${it.data}")

                    if (it.data != null) {

                        val noteDeleteDayFromDataStore = myNoteViewModel.getNoteDeleteDayFromDataStore()
                        val dataObserver = Observer<Int?> { data ->
                            noteDeleteDayFromDataStore.removeObservers(viewLifecycleOwner)

                            val cleanData: ArrayList<EntityMyNote> = arrayListOf()
                            val dataToBeDeleted: ArrayList<EntityMyNote> = arrayListOf()
                            val today = Util.smashDate(Util.dateTimeFormatter(Date()))
                            for (entity in it.data) {
                                val deletedDate :InternalDate? = Util.smashDate(entity.deletedDate ?: "")
                                if (deletedDate != null && today != null) {

                                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                                    val text = "${Integer.parseInt(deletedDate.year)}-${Integer.parseInt(deletedDate.month)}-${Integer.parseInt(deletedDate.day)}"
                                    val date : Date = formatter.parse(text)
                                    val sum : Long = date.time.plus(((data ?: NOTE_DELETE_DAY) * ONE_DAY_TIME))
                                    val newDate = Date(sum)

                                    if (Date().after(newDate)) {
                                        dataToBeDeleted.add(entity)
                                    }
                                    else {
                                        cleanData.add(entity)
                                    }


                                }
                            }

                            for (i in dataToBeDeleted) {
                                deleteNoteFromRoom(i)
                            }

                            deletedNoteAdapter.deletedNotes = cleanData

                        }

                        noteDeleteDayFromDataStore.observe(viewLifecycleOwner,dataObserver)


                    }
                    //binding.movieDetailLoading.makeGone()
                    //binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    //binding.movieDetailLoading.makeGone()
                    //binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun deleteNoteFromRoom(note: EntityMyNote) {
        val data = myNoteViewModel.deleteNoteFromRoom(note)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    //binding.movieDetailContainer.makeInVisible()
                    //binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("delete : ${it.data}")
                    val newList = deletedNoteAdapter.deletedNotes.toMutableList()
                    newList.remove(note)
                    deletedNoteAdapter.deletedNotes = newList

                    //binding.movieDetailLoading.makeGone()
                    //binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    //binding.movieDetailLoading.makeGone()
                    //binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun updateDeletedNoteFromRoom(note: EntityMyNote){
        val data = myNoteViewModel.updateDeletedNoteFromRoom(note.id ?: -1,note.isDeleted,note.deletedDate)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    //binding.movieDetailContainer.makeInVisible()
                    //binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("delete : ${it.data}")
                    getDeletedNotesFromRoom()
                    val newList = deletedNoteAdapter.deletedNotes.toMutableList()
                    newList.remove(note)
                    deletedNoteAdapter.deletedNotes = newList


                    //binding.movieDetailLoading.makeGone()
                    //binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    //binding.movieDetailLoading.makeGone()
                    //binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }

    private fun deleteAllNotesFromRoom(){
        val data = myNoteViewModel.deleteAllNotesFromRoom()

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    println("loading")
                    //binding.movieDetailContainer.makeInVisible()
                    //binding.movieDetailLoading.makeVisible()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("delete : ${it.data}")
                    deletedNoteAdapter.deletedNotes = listOf()
                    findNavController().popBackStack()


                    //binding.movieDetailLoading.makeGone()
                    //binding.movieDetailContainer.makeVisible()

                }
                is ResultData.Exception -> {
                    println("Exception")
                    //binding.movieDetailLoading.makeGone()
                    //binding.movieDetailContainer.makeVisible()

                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}