package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentUpdatePasswordBinding
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.presentation.fragments.GeneratePasswordFragment.*
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import chistousov.ilya.passwordkeeper.presentation.utils.getStringNullable
import chistousov.ilya.passwordkeeper.presentation.viewmodel.UpdatePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdatePasswordFragment : Fragment(R.layout.fragment_update_password), DataListener {

    private lateinit var binding: FragmentUpdatePasswordBinding
    private val viewModel: UpdatePasswordViewModel by viewModels()
    private val args: UpdatePasswordFragmentArgs by navArgs()
    private var generatePasswordFragment: GeneratePasswordFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdatePasswordBinding.bind(view)

        getPassword()
        updatePassword()
        deletePassword()
        generatePassword()
    }

    private fun getPassword() {
        lifecycleScope.launch {
            viewModel.getPassword(args.passwordId)
            viewModel.selectedPassword.collect {
                when (it) {
                    is UiState.Loading -> {
                        setupVisibility(binding.progressBar)
                    }
                    is UiState.Success -> {
                        setupVisibility(binding.rootContainer)
                        initFormFields(it.value)
                    }
                    is UiState.Error -> {
                        setupVisibility(binding.errorText)
                        binding.errorText.text = it.message
                    }
                }
            }
        }
    }

    private fun setupVisibility(visibleView: View) {
        listOf(binding.rootContainer, binding.progressBar, binding.errorText).forEach {
            it.isVisible = it == visibleView
        }
    }

    private fun initFormFields(password: PasswordModel) {
        binding.passwordDetails.titleEditText.setText(password.title)
        binding.passwordDetails.passwordEditText.setText(password.password)
        binding.passwordDetails.loginEditText.setText(password.login)
        binding.passwordDetails.emailEditText.setText(password.email)
        binding.passwordDetails.urlEditText.setText(password.url)
    }

    private fun updatePassword() {
        binding.passwordDetails.saveButton.setOnClickListener {
            val title = binding.passwordDetails.titleEditText.text.toString()
            val password = binding.passwordDetails.passwordEditText.text.toString()
            val login = binding.passwordDetails.loginEditText.text.toString()
            val email = binding.passwordDetails.emailEditText.text.toString()
            val url = binding.passwordDetails.urlEditText.text.toString()
            validateFields()
            viewModel.updatePassword(args.passwordId, title, password, login, email, url) {
                findNavController().popBackStack()
            }
        }
    }

    private fun deletePassword() {
        binding.deleteButton.setOnClickListener {
            viewModel.deletePassword(args.passwordId) {
                findNavController().popBackStack()
            }
        }
    }

    private fun validateFields() {
        val validatingFields = initValidatingFields()

        lifecycleScope.launch {
            viewModel.validatingFields.collect {
                it.forEach {
                    validatingFields[it.key]?.helperText = getStringNullable(it.value)
                }
            }
        }
    }

    private fun initValidatingFields() = mapOf(
        Validator.TITLE_KEY to binding.passwordDetails.titleContainer,
        Validator.PASSWORD_KEY to binding.passwordDetails.passwordContainer
    )

    private fun generatePassword() {
        binding.passwordDetails.generatePasswordButton.setOnClickListener {
            if (generatePasswordFragment == null) {
                generatePasswordFragment = GeneratePasswordFragment.newInstance()

                childFragmentManager.beginTransaction()
                    .replace(R.id.passwordGeneratorContainer, generatePasswordFragment!!)
                    .commit()
            } else {
                generatePasswordFragment?.generatePassword()
            }
        }
    }

    override fun onDataReceived(password: String) {
        binding.passwordDetails.titleEditText.setText(password)
    }
}