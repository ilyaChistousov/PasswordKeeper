package chistousov.ilya.passwordkeeper.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.FragmentPasswordListBinding
import chistousov.ilya.passwordkeeper.presentation.adapter.PasswordAdapter
import chistousov.ilya.passwordkeeper.presentation.viewmodel.PasswordListViewModel
import chistousov.ilya.passwordkeeper.utils.UiState
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

        getPasswordList()
        search()
        setupSwipeListener()
    }

    private fun search() {
        binding.searchEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.searchPassword(text.toString())
        }
    }

    private fun navigateToUpdatePassword(passwordId: Int) {
        val directions = PasswordListFragmentDirections
            .actionPasswordListToAddPassword(passwordId, ScreenMode.UPDATE)
        findNavController().navigate(directions)
    }

    private fun getPasswordList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passwordList.collect {
                    when (it) {
                        is UiState.Loading -> {
                            setupVisibility(binding.loadingBar)
                        }
                        is UiState.Success -> {
                            setupVisibility(binding.passwordsRecycler)
                            adapter.submitList(it.value)
                            binding.passwordsRecycler.adapter = adapter
                        }
                        is UiState.Error -> {
                            setupVisibility(binding.errorMessage)
                            binding.errorMessage.setText(it.message)
                        }
                    }
                }
            }
        }
    }

    private fun setupVisibility(visibleView: View) {
        listOf(binding.passwordsRecycler, binding.loadingBar, binding.errorMessage).forEach {
            if (it == visibleView) {
                it.visibility = View.VISIBLE
            } else {
                it.visibility = View.GONE
            }
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
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deletePassword(item.id)
            }
        }).attachToRecyclerView(binding.passwordsRecycler)
    }
}