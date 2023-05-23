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
import kotlin.math.log

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
    }


    private fun getPassword() {
        if (args.screenMode == ScreenMode.UPDATE) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getPassword(args.passwordId)
                    viewModel.selectedPassword.collect {
                        when (it) {
                            is PasswordState.Loading -> {
                                binding.containerLinear.visibility = View.GONE
                                binding.errorText.visibility = View.GONE
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is PasswordState.Success -> {
                                binding.containerLinear.visibility = View.VISIBLE
                                binding.errorText.visibility = View.GONE
                                binding.progressBar.visibility = View.GONE
                                initFields(it.value)
                            }

                            is PasswordState.Error -> {
                                binding.containerLinear.visibility = View.GONE
                                binding.progressBar.visibility = View.GONE
                                binding.errorText.visibility = View.VISIBLE
                                binding.errorText.text = getString(it.message)
                            }
                        }
                    }
                }
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
                ScreenMode.ADD-> {
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