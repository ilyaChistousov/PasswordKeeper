package chistousov.ilya.password_details.presentation.passwords

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.databinding.FragmentPasswordListBinding
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.presentation.passwords.adapter.PasswordAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordListFragment : Fragment(R.layout.fragment_password_list) {

    private val viewModel: PasswordListViewModel by viewModels()
    private lateinit var binding: FragmentPasswordListBinding
    private val adapter = PasswordAdapter(
        object : PasswordAdapter.ClickListener {
            override fun onPasswordItemClick(password: PasswordModel) {
                viewModel.launchUpdatePassword(password.id)
            }

            override fun onPasswordDeleteClick(password: PasswordModel) {
                viewModel.deletePassword(password.id)
            }

            override fun onPasswordCopyClick(password: PasswordModel) {
                copyPassword(password)
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordListBinding.bind(view)

        getPasswordList()
        search()
        navigateToCreatePassword()
        onBackPressed()
    }

    private fun search() {
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchPassword(text.toString())
        }
    }

    private fun navigateToCreatePassword() {
        binding.createPasswordButton.setOnClickListener {
            viewModel.launchCreatePassword()
        }
    }

    private fun getPasswordList() {
        viewModel.passwordListState.observe(viewLifecycleOwner) {
            if (it.isLoaded) {
                setupVisibility(binding.passwordsRecycler)
                adapter.submitList(it.passwordModelList)
                binding.passwordsRecycler.adapter = adapter
            } else {
                setupVisibility(binding.loadingBar)
            }
        }
    }

    private fun setupVisibility(visibleView: View) {
        listOf(binding.passwordsRecycler, binding.loadingBar, binding.errorMessage).forEach {
            it.isVisible = it == visibleView
        }
    }

    private fun copyPassword(password: PasswordModel) {
        val clipboardManager =
            binding.root.context.getSystemService(ClipboardManager::class.java)
        val clipData = ClipData.newPlainText("label", password.password)
        clipboardManager.setPrimaryClip(clipData)
        viewModel.showCopiedToast(getString(R.string.password_copied))
    }

    private fun onBackPressed() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().moveTaskToBack(true)
                }
            })
    }
}