package chistousov.ilya.password_details.presentation.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.databinding.FragmentCreatePasswordBinding
import chistousov.ilya.password_details.presentation.generate_password.GeneratePasswordFragment
import chistousov.ilya.presentation.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint

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
        validateFields()
    }

    private fun validateFields() {
        viewModel.createPasswordState.observe(viewLifecycleOwner) {
            binding.passwordDetails.passwordContainer.helperText = it.passwordError
            binding.passwordDetails.titleContainer.helperText = it.titleError
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

    override fun onDataReceived(password: String) {
        binding.passwordDetails.passwordEditText.setText(password)
    }
}