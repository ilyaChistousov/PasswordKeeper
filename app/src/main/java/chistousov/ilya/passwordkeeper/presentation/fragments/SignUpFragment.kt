package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentSignUpBinding
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import chistousov.ilya.passwordkeeper.presentation.utils.getStringNullable
import chistousov.ilya.passwordkeeper.presentation.utils.launchWhenStarted
import chistousov.ilya.passwordkeeper.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        collectSignUpState()
        signUp()
    }

    private fun collectSignUpState() {
        viewModel.checkRegistration()

        viewModel.signUpState.launchWhenStarted(viewLifecycleOwner) {
            if (it.isLoaded) {
                setupVisibility(binding.contentContainer)
                validatePassword(it.validationMap)
                if (it.isSignedUp) navigateToSignInFragment()
            } else {
                setupVisibility(binding.progressBar)
            }
        }
    }

    private fun setupVisibility(visibleView: View) {
        listOf(binding.progressBar, binding.contentContainer).forEach {
            it.isVisible = it == visibleView
        }
    }

    private fun validatePassword(validationMap: Map<String, Int?>) {
        val validateFields = initValidationContainerFields()

        validationMap.forEach {
            validateFields[it.key]?.helperText = getStringNullable(it.value)
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