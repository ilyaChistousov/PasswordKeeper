package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentSignUpBinding
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import chistousov.ilya.passwordkeeper.presentation.utils.getStringNullable
import chistousov.ilya.passwordkeeper.presentation.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding : FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        validatePassword()
        signUp()
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