package chistousov.ilya.password_details.presentation.update

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.databinding.FragmentUpdatePasswordBinding
import chistousov.ilya.password_details.domain.entity.PasswordField
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.presentation.generate_password.GeneratePasswordFragment
import chistousov.ilya.password_details.presentation.generate_password.GeneratePasswordFragment.GeneratedPasswordListener
import chistousov.ilya.presentation.BaseScreenArgs
import chistousov.ilya.presentation.args
import chistousov.ilya.presentation.observeEvent
import chistousov.ilya.presentation.viewModelFactory
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UpdatePasswordFragment : Fragment(R.layout.fragment_update_password),
    GeneratedPasswordListener {

    class Screen(
        val id: Int
    ) : BaseScreenArgs

    private lateinit var binding: FragmentUpdatePasswordBinding

    @Inject
    lateinit var factory: UpdatePasswordViewModel.Factory
    private val viewModel: UpdatePasswordViewModel by viewModelFactory {
        factory.create(args())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdatePasswordBinding.bind(view)

        updatePassword()
        observeState()
        observeEvent()
        generatePassword()
        deletePassword()
    }

    private fun observeState() {
        viewModel.updatePasswordState.observe(viewLifecycleOwner) { state ->
            if (state.isLoaded) {
                setupVisibility(binding.rootContainer)
                state.selectedPasswordModel?.let {
                    initFormFields(it)
                }
            } else {
                setupVisibility(binding.progressBar)
            }
        }
    }

    private fun observeEvent() {
        viewModel.focusFieldEventFlow.observeEvent(viewLifecycleOwner) {
            getEditTextByField(it).requestFocus()
            getEditTextByField(it).selectAll()
        }
        viewModel.fieldErrorMessageEventFlow.observeEvent(viewLifecycleOwner) {
            val textInput = getTextInputByField(it.first)
            textInput.helperText = it.second

            removeErrorOnChange(it.first)
        }
    }

    private fun setupVisibility(visibleView: View) {
        listOf(binding.rootContainer, binding.progressBar, binding.errorText).forEach {
            it.isVisible = it == visibleView
        }
    }

    private fun initFormFields(passwordModel: PasswordModel) {
        binding.passwordDetails.titleEditText.setText(passwordModel.title)
        binding.passwordDetails.passwordEditText.setText(passwordModel.password)
        binding.passwordDetails.loginEditText.setText(passwordModel.login)
        binding.passwordDetails.emailEditText.setText(passwordModel.email)
        binding.passwordDetails.urlEditText.setText(passwordModel.url)
    }

    private fun updatePassword() {
        binding.passwordDetails.saveButton.setOnClickListener {
            val title = binding.passwordDetails.titleEditText.text.toString()
            val password = binding.passwordDetails.passwordEditText.text.toString()
            val login = binding.passwordDetails.loginEditText.text.toString()
            val email = binding.passwordDetails.emailEditText.text.toString()
            val url = binding.passwordDetails.urlEditText.text.toString()

            viewModel.updatePassword(title, password, login, email, url)
        }
    }

    private fun deletePassword() {
        binding.deleteButton.setOnClickListener {
            viewModel.deletePassword()
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
        binding.passwordDetails.titleEditText.setText(password)
    }
}