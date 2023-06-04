package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentCreatePasswordBinding
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
import chistousov.ilya.passwordkeeper.presentation.utils.getStringNullable
import chistousov.ilya.passwordkeeper.presentation.viewmodel.CreatePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreatePasswordFragment : Fragment(R.layout.fragment_create_password),
    GeneratePasswordFragment.DataListener {

    private lateinit var binding: FragmentCreatePasswordBinding
    private val viewModel: CreatePasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreatePasswordBinding.bind(view)

        createPassword()
        generatePassword()
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

    private fun createPassword() {
        with(binding.passwordDetails) {
            saveButton.setOnClickListener {
                val title = titleEditText.text.toString()
                val password = passwordEditText.text.toString()
                val login = loginEditText.text.toString()
                val email = emailEditText.text.toString()
                val url = urlEditText.text.toString()

                validateFields()
                viewModel.createPassword(title, password, login, email, url) {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun generatePassword() {
        var generatePasswordFragment: GeneratePasswordFragment? = null
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
        binding.passwordDetails.passwordEditText.setText(password)
    }
}