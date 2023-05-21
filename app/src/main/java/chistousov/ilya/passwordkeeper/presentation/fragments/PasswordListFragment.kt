package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentPasswordListBinding
import chistousov.ilya.passwordkeeper.domain.usecase.DeletePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GetListPasswordUseCase
import chistousov.ilya.passwordkeeper.presentation.viewmodel.PasswordListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PasswordListFragment : Fragment(R.layout.fragment_password_list) {

    private val viewModel: PasswordListViewModel by viewModels()
    private lateinit var binding: FragmentPasswordListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordListBinding.bind(view)

        binding.addPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_passwordListFragment_to_addPasswordFragment)
        }

    }
}