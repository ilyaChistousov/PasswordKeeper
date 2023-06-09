package chistousov.ilya.password_details.presentation.update

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.databinding.FragmentUpdatePasswordBinding
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.presentation.generate_password.GeneratePasswordFragment
import chistousov.ilya.password_details.presentation.generate_password.GeneratePasswordFragment.DataListener
import chistousov.ilya.presentation.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePasswordFragment : Fragment(R.layout.fragment_update_password), DataListener {

    private lateinit var binding: FragmentUpdatePasswordBinding
    private val viewModel: UpdatePasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdatePasswordBinding.bind(view)

        updatePassword()
        getPassword()
        generatePassword()
        deletePassword()
    }

    private fun getPassword() {
//        viewModel.getPassword(args.passwordId)

        viewModel.updatePasswordState.observe(viewLifecycleOwner) {
            if (it.isLoaded) {
                setupVisibility(binding.rootContainer)
                it.selectedPasswordModel?.let {
                    initFormFields(it)
                }
            } else {
                setupVisibility(binding.progressBar)
            }
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

//            viewModel.updatePassword(args.passwordId, title, password, login, email, url)
        }
    }

    private fun deletePassword() {
        binding.deleteButton.setOnClickListener {
//            viewModel.deletePassword(args.passwordId)
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
        binding.passwordDetails.titleEditText.setText(password)
    }
}