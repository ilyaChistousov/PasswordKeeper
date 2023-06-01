package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentSignInBinding
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import chistousov.ilya.passwordkeeper.presentation.utils.getStringNullable
import chistousov.ilya.passwordkeeper.presentation.viewmodel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        binding.saveButton.setOnClickListener {
            viewModel.signIn(binding.passwordText.text.toString()) {
                navigateToTabsFragment()
            }
        }
    }

    private fun validateText() {
        val validationField = mapOf(Validator.PASSWORD_KEY to binding.passwordContainer)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passwordValidation.collect {
                    it.entries.forEach {
                        validationField[it.key]?.helperText = getStringNullable(it.value)
                    }
                }
            }
        }
    }


    private fun navigateToTabsFragment() {
        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
    }
}