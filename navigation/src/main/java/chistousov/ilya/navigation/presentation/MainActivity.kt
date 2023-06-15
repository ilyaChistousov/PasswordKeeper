package chistousov.ilya.navigation.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import chistousov.ilya.common_impl.ActivityRequired
import chistousov.ilya.navigation.databinding.ActivityMainBinding
import chistousov.ilya.navigation.presentation.navigation.NavComponentRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navComponentRouter: NavComponentRouter

    @Inject
    lateinit var activityRequired: Set<@JvmSuppressWildcards ActivityRequired>

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityRequired.forEach {
            it.onCreate(this)
        }
        navComponentRouter.setNavGraph()
    }
}