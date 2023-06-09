package chistousov.ilya.password_details.presentation.generate_password

import android.os.Bundle
import android.view.View
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.databinding.FragmentPasswordGeneratorBinding
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneratePasswordFragment : Fragment(R.layout.fragment_password_generator) {

    private lateinit var binding: FragmentPasswordGeneratorBinding
    private val viewModel: GeneratePasswordViewModel by viewModels()
    private var generatedPasswordListener: GeneratedPasswordListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordGeneratorBinding.bind(view)
        observeGeneratedPassword()
        generatedPasswordListener = (parentFragment as GeneratedPasswordListener)

        generatePassword()
        initGeneratorPasswordListeners()
    }

    fun generatePassword() {
        val passwordLength = binding.slider.value.toInt()
        val withDigitChar = binding.digitCharButton.isChecked
        val withUppercaseChar = binding.uppercaseCharButton.isChecked
        val withSpecialChar = binding.specialCharButton.isChecked
        viewModel.generatePassword(
            passwordLength, withDigitChar,
            withUppercaseChar, withSpecialChar
        )
    }

    private fun initGeneratorPasswordListeners() {
        val settingsButtons = binding.buttonsGroup.children as Sequence<MaterialButton>
        settingsButtons.forEach {
            it.setStrokeColorResource(android.R.color.transparent)
            it.isChecked = true
            it.setOnClickListener {
                generatePassword()
            }
        }

        binding.countCharText.text = binding.slider.value.toInt().toString()
        binding.slider.addOnChangeListener { _, value, _ ->
            binding.countCharText.text = value.toInt().toString()
            generatePassword()
        }
    }

    private fun observeGeneratedPassword() {
        viewModel.generatedPassword.observe(viewLifecycleOwner) {
            generatedPasswordListener?.onDataReceived(it.generatedPassword)
        }
    }

    interface GeneratedPasswordListener {
        fun onDataReceived(password: String)
    }

    companion object {
        fun newInstance() = GeneratePasswordFragment()
    }
}