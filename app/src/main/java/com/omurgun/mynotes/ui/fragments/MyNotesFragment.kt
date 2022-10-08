package com.omurgun.mynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.omurgun.mynotes.R
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.data.models.internal.InternalToolbarItems
import com.omurgun.mynotes.databinding.FragmentMyNotesBinding
import com.omurgun.mynotes.ui.activities.HomeActivity
import com.omurgun.mynotes.ui.adapters.adapterCallBack.ItemClickListener
import com.omurgun.mynotes.ui.adapters.recyclerViewAdapter.MyNoteAdapter
import com.omurgun.mynotes.ui.factory.ViewModelFactory
import com.omurgun.mynotes.ui.util.*
import com.omurgun.mynotes.ui.viewModel.MyNoteViewModel
import java.util.*
import javax.inject.Inject


class MyNotesFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
): Fragment() {
    private var _binding: FragmentMyNotesBinding? = null
    private val binding get() = _binding!!
    private val myNoteViewModel: MyNoteViewModel by viewModels{viewModelFactory}
    private val myNoteAdapter: MyNoteAdapter = MyNoteAdapter()
    private var toolbarItems: InternalToolbarItems? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyNotesBinding.inflate(inflater,container,false)
        toolbarItems = (requireActivity() as HomeActivity).getToolbarItems()
        (requireActivity() as HomeActivity).supportActionBar?.show()
        toolbarItems?.titleText?.makeVisible()
        toolbarItems?.endButton?.setImageDrawable(Util.getDrawable(R.drawable.ic_baseline_settings_24,requireContext().theme))
        toolbarItems?.endButton?.makeVisible()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDeletedNotesFromRoom()
        getAllNotesFromRoom()

        binding.myNotesRv.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = myNoteAdapter
        }
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.myNotesRv)
        myNoteAdapter.listener = adapterItemClickListener

        binding.addNoteFab.setOnClickListener {
            toolbarItems?.titleText?.makeInVisible()
            toolbarItems?.secondEndButton?.makeInVisible()
            toolbarItems?.trashItemCountText?.makeInVisible()
            toolbarItems?.trashItemCountTextBackgroundView?.makeInVisible()
            val action = MyNotesFragmentDirections.actionMyNotesFragmentToAddNoteFragment()
            findNavController().navigate(action)
        }

        toolbarItems?.secondEndButton?.setOnClickListener {
            toolbarItems?.titleText?.makeInVisible()
            toolbarItems?.secondEndButton?.makeInVisible()
            toolbarItems?.trashItemCountText?.makeInVisible()
            toolbarItems?.trashItemCountTextBackgroundView?.makeInVisible()

            val action = MyNotesFragmentDirections.actionMyNotesFragmentToDeletedNotesFragment()
            findNavController().navigate(action)
        }

        toolbarItems?.endButton?.setOnClickListener {

            toolbarItems?.titleText?.makeInVisible()
            toolbarItems?.secondEndButton?.makeInVisible()
            toolbarItems?.trashItemCountText?.makeInVisible()
            toolbarItems?.trashItemCountTextBackgroundView?.makeInVisible()
            toolbarItems?.endButton?.makeInVisible()

            val action = MyNotesFragmentDirections.actionMyNotesFragmentToSettingsFragment()
            findNavController().navigate(action)
        }

        binding.searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.isNotEmpty()) {
                    searchByTitleOrDetailFromRoom(newText)
                }
                else {
                    getAllNotesFromRoom()
                }

                return false
            }

        })




    }

    private val adapterItemClickListener = object : ItemClickListener {
        override fun onItemClicked(position: Int) {
            toolbarItems?.titleText?.makeInVisible()
            toolbarItems?.secondEndButton?.makeInVisible()
            toolbarItems?.trashItemCountText?.makeInVisible()
            toolbarItems?.trashItemCountTextBackgroundView?.makeInVisible()
            val action = MyNotesFragmentDirections.actionMyNotesFragmentToUpdateNoteFragment(id)
            findNavController().navigate(action)
        }


    }


    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedNote = myNoteAdapter.myNotes[layoutPosition]
            selectedNote.isDeleted = true
            selectedNote.deletedDate = Util.dateTimeFormatter(Date())
            updateDeletedNoteFromRoom(selectedNote,layoutPosition)

        }

    }


    private fun getAllNotesFromRoom(){
        val data = myNoteViewModel.getAllNotesFromRoom()

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).dialogLoading?.show()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("data : ${it.data}")
                    if (it.data != null) {
                        myNoteAdapter.myNotes = it.data
                    }


                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
                is ResultData.Exception -> {
                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
            }
        }

    }

    private fun searchByTitleOrDetailFromRoom(search: String){
        val data = myNoteViewModel.searchByTitleOrDetailFromRoom("%$search%")

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).dialogLoading?.show()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("search : ${it.data}")
                    if (it.data != null) {
                        myNoteAdapter.myNotes = it.data
                    }

                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
                is ResultData.Exception -> {
                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
            }
        }

    }

    private fun deleteNoteFromRoom(note: EntityMyNote,removePosition: Int){
        val data = myNoteViewModel.deleteNoteFromRoom(note)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).dialogLoading?.show()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("delete : ${it.data}")
                    val newList = myNoteAdapter.myNotes.toMutableList()
                    newList.remove(note)
                    myNoteAdapter.myNotes = newList

                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
                is ResultData.Exception -> {


                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
            }
        }

    }

    private fun updateDeletedNoteFromRoom(note: EntityMyNote,removePosition: Int){
        val data = myNoteViewModel.updateDeletedNoteFromRoom(note.id ?: -1,note.isDeleted,note.deletedDate)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).dialogLoading?.show()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("delete : ${it.data}")
                    getDeletedNotesFromRoom()
                    val newList = myNoteAdapter.myNotes.toMutableList()
                    newList.remove(note)
                    myNoteAdapter.myNotes = newList


                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
                is ResultData.Exception -> {
                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()

                }
            }
        }

    }



    private fun getDeletedNotesFromRoom() {
        val data = myNoteViewModel.getDeletedNotesFromRoom()

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).dialogLoading?.show()
                }
                is ResultData.Success -> {
                    if (it.data != null && it.data.isNotEmpty()) {
                        toolbarItems?.secondEndButton?.setImageDrawable(Util.getDrawable(R.drawable.ic_baseline_restore_from_trash_24,requireContext().theme))
                        toolbarItems?.secondEndButton?.makeVisible()
                        toolbarItems?.trashItemCountTextBackgroundView?.makeVisible()

                        toolbarItems?.trashItemCountText?.makeVisible()
                        toolbarItems?.trashItemCountText?.makeVisible()
                        toolbarItems?.trashItemCountText?.text = it.data.size.toString()

                    }
                    else {
                        toolbarItems?.trashItemCountTextBackgroundView?.makeInVisible()
                        toolbarItems?.secondEndButton?.makeInVisible()
                    }

                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
                is ResultData.Exception -> {
                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
            }
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

