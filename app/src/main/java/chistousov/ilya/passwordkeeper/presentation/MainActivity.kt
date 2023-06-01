package chistousov.ilya.passwordkeeper.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.databinding.ActivityMainBinding
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        checkRegistration()
    }

    private fun checkRegistration() {
        viewModel.checkRegistration()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isRegistered.collect {
                    when (it) {
                        is UiState.Loading -> {
                            setVisibility(binding.progressBar)
                        }
                        is UiState.Success -> {
                            setStartDestination(it.value)
                            setVisibility(binding.fragmentContainer)
                        }
                        is UiState.Error -> {
                            throw IllegalStateException("Unexpected value: $it")
                        }
                    }
                }
            }
        }
    }

    private fun setVisibility(visibleView: View) {
        listOf(binding.progressBar, binding.fragmentContainer).forEach {
            it.isVisible = it == visibleView
        }
    }

    private fun setStartDestination(isRegistered: Boolean) {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHost.navController
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        graph.setStartDestination(
            if (isRegistered) {
                R.id.signInFragment
            } else {
                R.id.signUpFragment
            }
        )
        navController.graph = graph
    }
}