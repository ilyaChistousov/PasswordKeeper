package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentPasswordListBinding
import chistousov.ilya.passwordkeeper.presentation.adapter.PasswordAdapter
import chistousov.ilya.passwordkeeper.presentation.viewmodel.PasswordListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PasswordListFragment : Fragment(R.layout.fragment_password_list) {

    private val viewModel: PasswordListViewModel by viewModels()
    private lateinit var binding: FragmentPasswordListBinding
    private val adapter = PasswordAdapter {
        navigateToUpdatePassword(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordListBinding.bind(view)

        initRecyclerView()

        search()
        navigateToAddPassword()
    }

    private fun search() {
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchPassword(text.toString())
        }
    }

    private fun navigateToAddPassword() {
        binding.addPasswordButton.setOnClickListener {
            val direction = PasswordListFragmentDirections
                .actionPasswordListFragmentToPasswordDetailsFragment(ScreenMode.ADD)
            findNavController().navigate(direction)
        }
    }

    private fun navigateToUpdatePassword(passwordId: Int) {
        val directions = PasswordListFragmentDirections
            .actionPasswordListFragmentToPasswordDetailsFragment(ScreenMode.UPDATE, passwordId)
        findNavController().navigate(directions)
    }

    private fun initRecyclerView() {
        lifecycleScope.launch {
            viewModel.passwordList.collect {
                adapter.submitList(it)
            }
        }
        binding.passwordsRecycler.adapter = adapter
    }
}