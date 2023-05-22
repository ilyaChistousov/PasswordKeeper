package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentAddPasswordBinding
import chistousov.ilya.passwordkeeper.presentation.viewmodel.AddPasswordViewModel
import chistousov.ilya.passwordkeeper.utils.Validator
import chistousov.ilya.passwordkeeper.utils.getStringNullable
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPasswordFragment : Fragment(R.layout.fragment_add_password) {

    private val viewModel: AddPasswordViewModel by viewModels()
    private lateinit var binding: FragmentAddPasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddPasswordBinding.bind(view)

        createPassword()
    }

    private fun createPassword() {
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val login = binding.loginEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val url = binding.urlEditText.text.toString()
            val validationField = initValidationFields()
            lifecycleScope.launch {
                viewModel.passwordValidation.collect {
                    it.entries.forEach {
                        validationField[it.key]?.helperText = getStringNullable(it.value)
                    }
                }
            }
            viewModel.createPassword(title, password, login, email, url) {
                navigateToPasswordList()
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