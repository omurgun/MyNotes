package com.omurgun.mynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.data.models.internal.InternalToolbarItems
import com.omurgun.mynotes.databinding.FragmentAddNoteBinding
import com.omurgun.mynotes.ui.activities.HomeActivity
import com.omurgun.mynotes.ui.factory.ViewModelFactory
import com.omurgun.mynotes.ui.util.*
import com.omurgun.mynotes.ui.viewModel.MyNoteViewModel
import java.util.*
import javax.inject.Inject


class AddNoteFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : Fragment() {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private val myNoteViewModel: MyNoteViewModel by viewModels { viewModelFactory }
    private var toolbarItems: InternalToolbarItems? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        toolbarItems = (requireActivity() as HomeActivity).getToolbarItems()
        toolbarItems?.endButton?.setImageDrawable(Util.getDrawable(com.omurgun.mynotes.R.drawable.ic_baseline_check_24,requireContext().theme))
        (requireActivity() as HomeActivity).supportActionBar?.show()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detailInputLayout.setOnClickListener {

            binding.detailInput.requestFocus()
            toggleKeyboard()


        }

        toolbarItems?.endButton?.setOnClickListener {
            insertNoteToRoom(
                EntityMyNote(
                    title = binding.titleInput.text.toString(),
                    detail = binding.detailInput.text.toString(),
                    createdDate = Util.dateTimeFormatter(Date()),
                    updatedDate = Util.dateTimeFormatter(Date()),
                    lastVisitDate = Util.dateTimeFormatter(Date())
                )
            )
        }



        binding.titleInput.doAfterTextChanged {
            if (it.isNullOrBlank() || binding.detailInput.text.isNullOrBlank()) {
                toolbarItems?.endButton?.makeInVisible()
            }
            else {
                if (toolbarItems?.endButton?.isVisible == false) {
                    toolbarItems?.endButton?.makeVisible()
                }
            }
        }


        binding.detailInput.doAfterTextChanged {
            if (it.isNullOrBlank() || binding.titleInput.text.isNullOrBlank()) {
                toolbarItems?.endButton?.makeInVisible()
            }
            else {
                if (toolbarItems?.endButton?.isVisible == false) {
                    toolbarItems?.endButton?.makeVisible()
                }

            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertNoteToRoom(note: EntityMyNote) {
        val data = myNoteViewModel.insertNoteToRoom(note)

        data.observe(viewLifecycleOwner) {
            when (it) {
                is ResultData.Loading -> {
                    (requireActivity() as HomeActivity).dialogLoading?.show()
                }
                is ResultData.Success -> {
                    hideKeyboard()
                    findNavController().popBackStack()

                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()
                }
                is ResultData.Exception -> {

                    (requireActivity() as HomeActivity).dialogLoading?.dismiss()

                }
            }
        }

    }
}