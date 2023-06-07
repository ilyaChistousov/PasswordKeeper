package chistousov.ilya.sign_up.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import chistousov.ilya.presentation.launchWhenStarted
import chistousov.ilya.sign_up.R
import chistousov.ilya.sign_up.databinding.FragmentSignUpBinding
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
                binding.passwordConfirmText.setText(it.confirmPasswordError)
                binding.passwordCreatingText.setText(it.passwordError)
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
}