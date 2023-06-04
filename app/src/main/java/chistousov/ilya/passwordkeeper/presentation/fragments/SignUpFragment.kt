package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentSignUpBinding
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import chistousov.ilya.passwordkeeper.presentation.utils.getStringNullable
import chistousov.ilya.passwordkeeper.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        checkRegistration()
        validatePassword()
        signUp()
    }

    private fun checkRegistration() {
        viewModel.checkRegistration()
        lifecycleScope.launch {
            viewModel.isSignedUp.collect {
                when (it) {
                    is UiState.Loading -> {
                        setupVisibility(binding.progressBar)
                    }
                    is UiState.Success -> {
                        if (it.value) {
                            navigateToSignInFragment()
                        }
                        setupVisibility(binding.contentContainer)
                    }
                    else -> throw IllegalStateException("Unexpected value: $it")
                }
            }
        }
    }

    private fun setupVisibility(visibleView: View) {
        listOf(binding.progressBar, binding.contentContainer).forEach {
            it.isVisible = it == visibleView
        }
    }

    private fun validatePassword() {
        val validateFields = initValidationContainerFields()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passwordValidation.collect {
                    it.forEach {
                        validateFields[it.key]?.helperText = getStringNullable(it.value)
                    }
                }
            }
        }
    }

    private fun signUp() {
        binding.signUpButton.setOnClickListener {
            val password = binding.passwordCreatingText.text.toString()
            val confirmPassword = binding.passwordConfirmText.text.toString()
            viewModel.signUp(password, confirmPassword) { navigateToSignInFragment() }
        }
    }

    private fun initValidationContainerFields() = mapOf(
        Validator.PASSWORD_KEY to binding.passwordContainer,
        Validator.CONFIRM_PASSWORD_KEY to binding.passwordConfirmContainer
    )

    private fun navigateToSignInFragment() {
        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }
}