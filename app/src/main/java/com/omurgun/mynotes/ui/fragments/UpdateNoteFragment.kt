package com.omurgun.mynotes.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.omurgun.mynotes.R
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.data.models.internal.InternalToolbarItems
import com.omurgun.mynotes.databinding.FragmentUpdateNoteBinding
import com.omurgun.mynotes.ui.activities.HomeActivity
import com.omurgun.mynotes.ui.factory.ViewModelFactory
import com.omurgun.mynotes.ui.util.*
import com.omurgun.mynotes.ui.viewModel.MyNoteViewModel
import java.util.*
import javax.inject.Inject

class UpdateNoteFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : Fragment() {
    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!
    private val myNoteViewModel: MyNoteViewModel by viewModels { viewModelFactory }
    private var toolbarItems: InternalToolbarItems? = null
    private var currentNoteId: Int? = null
    private var currentNote: EntityMyNote? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        toolbarItems = (requireActivity() as HomeActivity).getToolbarItems()
        (requireActivity() as HomeActivity).supportActionBar?.show()
        toolbarItems?.endButton?.setImageDrawable(
            Util.getDrawable(
                R.drawable.ic_baseline_check_24,
                requireContext().theme
            )
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val args by navArgs<UpdateNoteFragmentArgs>()
            currentNoteId = args.noteId
        }

        currentNoteId?.let { getNoteWithIdFromRoom(it) }

        toolbarItems?.endButton?.setOnClickListener {

            if (currentNote != null) {
                updateNoteFromRoom(
                    EntityMyNote(
                        currentNoteId, binding.titleInput.text.toString(), binding.detailInput.text.toString(), currentNote!!.createdDate,
                        Util.dateTimeFormatter(Date()),
                        Util.dateTimeFormatter(Date())
                        ),
                false
                )
            }

        }




        binding.titleInput.doAfterTextChanged {
            if (it.isNullOrEmpty() || binding.detailInput.text.isNullOrEmpty()) {
                toolbarItems?.endButton?.makeInVisible()
            } else {
                if (toolbarItems?.endButton?.isVisible == false) {
                    toolbarItems?.endButton?.makeVisible()
                }
            }
        }

        binding.detailInput.doAfterTextChanged {
            if (it.isNullOrEmpty() || binding.titleInput.text.isNullOrEmpty()) {
                toolbarItems?.endButton?.makeInVisible()
            } else {
                if (toolbarItems?.endButton?.isVisible == false) {
                    toolbarItems?.endButton?.makeVisible()
                }

            }
        }




    }


    private fun updateNoteFromRoom(note: EntityMyNote,isOnlyLastVisit:Boolean) {
        val data = myNoteViewModel.updateNoteFromRoom(note)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).dialogLoading?.show()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("data : ${it.data}")
                    if (!isOnlyLastVisit) findNavController().popBackStack()

                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
                is ResultData.Exception -> {
                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
            }
        }

    }

    private fun getNoteWithIdFromRoom(noteId: Int) {
        val data = myNoteViewModel.getNoteWithIdFromRoom(noteId)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).dialogLoading?.show()
                }
                is ResultData.Success -> {
                    println("Success")
                    println("data : ${it.data}")

                    if (it.data != null) {
                        binding.titleInput.setText(it.data.title.toString())
                        binding.detailInput.setText(it.data.detail.toString())
                        currentNote = it.data

                        updateNoteFromRoom(
                            EntityMyNote(
                                it.data.id, it.data.title, it.data.detail, it.data.createdDate,
                                it.data.updatedDate,
                                Util.dateTimeFormatter(Date())
                            ),true)

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