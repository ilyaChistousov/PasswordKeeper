package chistousov.ilya.password_details.presentation.create

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.databinding.FragmentCreatePasswordBinding
import chistousov.ilya.password_details.domain.entity.PasswordField
import chistousov.ilya.password_details.presentation.generate_password.GeneratePasswordFragment
import chistousov.ilya.presentation.observeEvent
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePasswordFragment : Fragment(R.layout.fragment_create_password),
    GeneratePasswordFragment.GeneratedPasswordListener {

    private lateinit var binding: FragmentCreatePasswordBinding
    private val viewModel: CreatePasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreatePasswordBinding.bind(view)

        createPassword()
        generatePassword()
        observeEvent()
        observeState()
    }

    private fun observeEvent() {
        viewModel.focusFieldEventFlow.observeEvent(viewLifecycleOwner) {
            getEditTextByField(it).requestFocus()
            getEditTextByField(it).selectAll()
        }
    }

    private fun observeState() {
        viewModel.createPasswordState.observe(viewLifecycleOwner) { state ->
            if (state.fieldError != null) {
                val textInput = getTextInputByField(state.fieldError.first)
                textInput.helperText = state.fieldError.second

                removeErrorOnChange(state.fieldError.first)
            }
        }
    }

    private fun createPassword() {
        with(binding.passwordDetails) {
            saveButton.setOnClickListener {
                val title = titleEditText.text.toString()
                val password = passwordEditText.text.toString()
                val login = loginEditText.text.toString()
                val email = emailEditText.text.toString()
                val url = urlEditText.text.toString()

                viewModel.createPassword(title, password, login, email, url)
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

    private fun getEditTextByField(field: PasswordField): EditText {
        return when (field) {
            PasswordField.TITLE -> binding.passwordDetails.titleEditText
            PasswordField.PASSWORD -> binding.passwordDetails.passwordEditText
        }
    }

    private fun getTextInputByField(field: PasswordField): TextInputLayout {
        return when (field) {
            PasswordField.TITLE -> binding.passwordDetails.titleContainer
            PasswordField.PASSWORD -> binding.passwordDetails.passwordContainer
        }
    }

    private fun removeErrorOnChange(field: PasswordField) {
        val editText = getEditTextByField(field)
        editText.addTextChangedListener {
            getTextInputByField(field).helperText = null
        }
    }

    override fun onDataReceived(password: String) {
        binding.passwordDetails.passwordEditText.setText(password)
    }
}