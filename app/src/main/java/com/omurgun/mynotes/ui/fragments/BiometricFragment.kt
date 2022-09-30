package com.omurgun.mynotes.ui.fragments

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Bundle
import android.os.CancellationSignal
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.omurgun.mynotes.R
import com.omurgun.mynotes.data.models.entity.EntityMyNote
import com.omurgun.mynotes.data.models.internal.InternalToolbarItems
import com.omurgun.mynotes.databinding.FragmentBiometricBinding
import com.omurgun.mynotes.databinding.FragmentUpdateNoteBinding
import com.omurgun.mynotes.ui.activities.HomeActivity
import com.omurgun.mynotes.ui.factory.ViewModelFactory
import com.omurgun.mynotes.ui.util.ResultData
import com.omurgun.mynotes.ui.util.Util
import com.omurgun.mynotes.ui.util.makeInVisible
import com.omurgun.mynotes.ui.util.makeVisible
import com.omurgun.mynotes.ui.viewModel.MyNoteViewModel
import java.util.*
import javax.inject.Inject


class BiometricFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory
) : Fragment() {
    private var _binding: FragmentBiometricBinding? = null
    private val binding get() = _binding!!

    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication success!")
                    val action = BiometricFragmentDirections.actionBiometricFragmentToMyNotesFragment()
                    findNavController().navigate(action)
                }
            }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBiometricBinding.inflate(inflater, container, false)
        (requireActivity() as HomeActivity).supportActionBar?.hide()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkBiometricSupport()

        binding.fingerprintImageView.setOnClickListener {

            val biometricPrompt: BiometricPrompt = BiometricPrompt.Builder(requireContext())
                .setTitle("Title of prompt")
                .setSubtitle("Authentication is required")
                .setDescription("This app uses fingerprint protection to keep your data secure")
                .setNegativeButton("Cancel", requireActivity().mainExecutor) { dialogInterface, which ->
                    notifyUser("Authentication cancelled")
                }.build()

            biometricPrompt.authenticate(getCancellationSignal(),requireActivity().mainExecutor,authenticationCallback)
        }

    }

    private fun notifyUser(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was canceled by the user")
        }
        return  cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport() : Boolean {
        val keyguardManager: KeyguardManager = requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            notifyUser("Fingerprint authentication has not been enable in settings")
            return false
        }

        if (ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint authentication permission is not enabled")
            return false
        }

        return if(requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}