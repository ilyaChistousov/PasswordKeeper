package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.children
import androidx.core.view.isVisible
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
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import chistousov.ilya.passwordkeeper.presentation.utils.getStringNullable
import chistousov.ilya.passwordkeeper.presentation.viewmodel.PasswordDetailsViewModel
import com.google.android.material.button.MaterialButton
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
    private val generatePasswordButtons by lazy {
        binding.passwordGeneratorSettings.buttonsGroup.children as Sequence<MaterialButton>
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordDetailsBinding.bind(view)

        getPassword()
        createPassword()
        deletePassword()
        initGeneratorPasswordListeners()
        obBackPressedListener()
    }

    private fun getPassword() {
        if (args.screenMode == ScreenMode.UPDATE) {
            binding.deleteButton.visibility = View.VISIBLE

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.getPassword(args.passwordId)
                    viewModel.selectedPassword.collect {
                        when (it) {
                            is UiState.Loading -> {
                                setupVisibility(binding.progressBar)
                            }

                            is UiState.Success -> {
                                setupVisibility(binding.containerLinear)
                                initFormFields(it.value)
                            }

                            is UiState.Error -> {
                                setupVisibility(binding.errorText)
                                binding.errorText.text = getString(it.resId)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupVisibility(visibleView: View) {
        listOf(binding.containerLinear, binding.progressBar, binding.errorText).forEach {
            it.isVisible = it == visibleView
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

    private fun generatePassword() {
        with(binding.passwordGeneratorSettings) {
            val passwordLength = slider.value.toInt()
            val withDigitChar = digitCharButton.isChecked
            val withUppercaseChar = uppercaseCharButton.isChecked
            val withSpecialChar = specialCharButton.isChecked
            viewModel.generatePassword(
                passwordLength, withDigitChar,
                withUppercaseChar, withSpecialChar
            )
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

    private fun initGeneratorPasswordListeners() {
        binding.generatePasswordButton.setOnClickListener {
            binding.passwordGeneratorSettings.root.visibility = View.VISIBLE
            generatePassword()
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.generatedPassword.collect {
                        binding.passwordEditText.setText(it)
                    }
                }
            }
        }

        generatePasswordButtons.forEach {
            it.setStrokeColorResource(android.R.color.transparent)
            it.isChecked = true
            it.setOnClickListener {
                generatePassword()
            }
        }

        with(binding.passwordGeneratorSettings) {
            countCharText.text = slider.value.toInt().toString()
            slider.addOnChangeListener { _, value, _ ->
                countCharText.text = value.toInt().toString()
                generatePassword()
            }
        }
    }

    private fun initFormFields(password: PasswordModel) {
        binding.titleEditText.setText(password.title)
        binding.passwordEditText.setText(password.password)
        binding.loginEditText.setText(password.login)
        binding.emailEditText.setText(password.email)
        binding.urlEditText.setText(password.url)
    }

    private fun initValidationFields() = mapOf(
        Validator.TITLE_KEY to binding.titleContainer,
        Validator.PASSWORD_KEY to binding.passwordContainer
    )

    private fun obBackPressedListener() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToPasswordList()
            }
        })
    }

    private fun navigateToPasswordList() {
        findNavController().popBackStack()
    }

}