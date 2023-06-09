package chistousov.ilya.sign_in.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import chistousov.ilya.sign_in.R
import chistousov.ilya.sign_in.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)

        validateText()
        signIn()
    }

    private fun signIn() {
        binding.signInButton.setOnClickListener {
            viewModel.signIn(binding.passwordText.text.toString())
        }
    }

    private fun validateText() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            binding.passwordContainer.helperText = it.errorMessage
        }
    }
}