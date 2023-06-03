package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentPasswordListBinding
import chistousov.ilya.passwordkeeper.presentation.adapter.PasswordAdapter
import chistousov.ilya.passwordkeeper.presentation.utils.launchWhenStarted
import chistousov.ilya.passwordkeeper.presentation.viewmodel.PasswordListViewModel
import dagger.hilt.android.AndroidEntryPoint

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

        getPasswordList()
        search()
        setupSwipeListener()
        navigateToCreatePassword()
    }

    private fun search() {
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchPassword(text.toString())
        }
    }

    private fun navigateToUpdatePassword(passwordId: Int) {
        val directions = PasswordListFragmentDirections
            .actionPasswordListFragmentToUpdatePasswordFragment(passwordId)
        findNavController().navigate(directions)
    }

    private fun navigateToCreatePassword() {
        binding.createPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_passwordListFragment_to_createPasswordFragment)
        }
    }

    private fun getPasswordList() {
        viewModel.passwordListState.launchWhenStarted(viewLifecycleOwner) {
            if (it.isLoaded) {
                setupVisibility(binding.passwordsRecycler)
                adapter.submitList(it.passwordList)
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

    private fun setupSwipeListener() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedItem = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deletePassword(swipedItem.id)
            }
        }).attachToRecyclerView(binding.passwordsRecycler)
    }
}