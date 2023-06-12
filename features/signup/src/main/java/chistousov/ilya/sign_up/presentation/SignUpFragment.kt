package chistousov.ilya.sign_up.presentation

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import chistousov.ilya.presentation.observeEvent
import chistousov.ilya.sign_up.R
import chistousov.ilya.sign_up.databinding.FragmentSignUpBinding
import chistousov.ilya.sign_up.domain.entity.SignUpField
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpBinding.bind(view)

        observeSignUpState()
        observeEvents()
        signUp()
    }

    private fun observeEvents() {
        viewModel.clearFieldFlowEvent.observeEvent(viewLifecycleOwner) {
            getEditTextByField(it).text.clear()
        }

        viewModel.focusFieldFlowEvent.observeEvent(viewLifecycleOwner) {
            getEditTextByField(it).requestFocus()
            getEditTextByField(it).selectAll()
        }
    }

    private fun observeSignUpState() {
        viewModel.checkRegistration()

        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            if (state.isLoaded) {
                setupVisibility(binding.contentContainer)

                cleanUpErrors()
                if (state.fieldErrorMessage != null) {
                    val textInput = getTextInputByField(state.fieldErrorMessage.first)
                    textInput.helperText = state.fieldErrorMessage.second
                }
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

    private fun signUp() {
        binding.signUpButton.setOnClickListener {
            val password = binding.passwordCreatingText.text.toString()
            val confirmPassword = binding.passwordConfirmText.text.toString()
            viewModel.signUp(password, confirmPassword)
        }
    }

    private fun getEditTextByField(field: SignUpField): EditText {
        return when (field) {
            SignUpField.PASSWORD -> binding.passwordCreatingText
            SignUpField.CONFIRM_PASSWORD -> binding.passwordConfirmText
        }
    }

    private fun getTextInputByField(field: SignUpField): TextInputLayout {
        return when (field) {
            SignUpField.PASSWORD -> binding.passwordContainer
            SignUpField.CONFIRM_PASSWORD -> binding.passwordConfirmContainer
        }
    }

    private fun cleanUpErrors() {
        binding.passwordContainer.helperText = null
        binding.passwordConfirmContainer.helperText = null
    }
}