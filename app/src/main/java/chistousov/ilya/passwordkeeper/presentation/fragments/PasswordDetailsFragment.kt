package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentPasswordDetailsBinding
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.presentation.viewmodel.PasswordDetailsViewModel
import chistousov.ilya.passwordkeeper.utils.PasswordState
import chistousov.ilya.passwordkeeper.utils.Validator
import chistousov.ilya.passwordkeeper.utils.getStringNullable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

enum class ScreenMode {
    UPDATE, ADD
}

@AndroidEntryPoint
class PasswordDetailsFragment : Fragment(R.layout.fragment_password_details) {

    private val viewModel: PasswordDetailsViewModel by viewModels()
    private lateinit var binding: FragmentPasswordDetailsBinding
    private val args: PasswordDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordDetailsBinding.bind(view)

        getPassword()
        createPassword()
        deletePassword()
    }


    private fun getPassword() {
        if (args.screenMode == ScreenMode.UPDATE) {
            binding.deleteButton.visibility = View.VISIBLE

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getPassword(args.passwordId)
                    viewModel.selectedPassword.collect {
                        when (it) {
                            is PasswordState.Loading -> {
                                setupVisibility(binding.progressBar)
                            }

                            is PasswordState.Success -> {
                                setupVisibility(binding.containerLinear)
                                initFields(it.value)
                            }

                            is PasswordState.Error -> {
                                setupVisibility(binding.errorText)
                                binding.errorText.text = getString(it.message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupVisibility(visibleView: View) {
        listOf(binding.containerLinear, binding.progressBar, binding.errorText).forEach {
            if (it == visibleView) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
        }
    }

    private fun createPassword() {
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val login = binding.loginEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val url = binding.urlEditText.text.toString()
            when (args.screenMode) {
                ScreenMode.ADD -> {
                    validateFields()
                    viewModel.createPassword(title, password, login, email, url) {
                        navigateToPasswordList()
                    }
                }

                ScreenMode.UPDATE -> {
                    validateFields()
                    viewModel.updatePassword(args.passwordId, title, password, login, email, url) {
                        navigateToPasswordList()
                    }
                }
            }

        }
    }

    private fun deletePassword() {
        binding.deleteButton.setOnClickListener {
            viewModel.deletePassword(args.passwordId) {
                navigateToPasswordList()
            }
        }
    }

    private fun initFields(password: PasswordModel) {
        with(binding) {
            titleEditText.setText(password.title)
            passwordEditText.setText(password.password)
            loginEditText.setText(password.login)
            emailEditText.setText(password.email)
            urlEditText.setText(password.url)
        }
    }

    private fun validateFields() {
        val validationField = initValidationFields()

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

    private fun initValidationFields() = mapOf(
        Validator.TITLE_KEY to binding.titleContainer,
        Validator.PASSWORD_KEY to binding.passwordContainer
    )

    private fun navigateToPasswordList() {
        findNavController().popBackStack()
    }
}