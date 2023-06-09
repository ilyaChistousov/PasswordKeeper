package chistousov.ilya.password_details.presentation.passwords

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.databinding.FragmentPasswordListBinding
import chistousov.ilya.password_details.presentation.passwords.adapter.PasswordAdapter
import chistousov.ilya.presentation.launchWhenStarted
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordListFragment : Fragment(R.layout.fragment_password_list) {

    private val viewModel: PasswordListViewModel by viewModels()
    private lateinit var binding: FragmentPasswordListBinding
    private val adapter = PasswordAdapter {
        viewModel.launchUpdatePassword(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPasswordListBinding.bind(view)

        getPasswordList()
        search()
        setupSwipeListener()
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