package chistousov.ilya.navigation.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import chistousov.ilya.navigation.presentation.navigation.NavComponentRouter
import chistousov.ilya.navigation.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    @Inject
//    lateinit var navComponentRouterFactory: NavComponentRouter.Factory

//    private val navComponentRouter by lazy(LazyThreadSafetyMode.NONE) {
//        navComponentRouterFactory.create(
//            R.id.fragmentContainer
//        )
//    }

    @Inject
    lateinit var navComponentRouter: NavComponentRouter

    private lateinit var binding: ActivityMainBinding

//    @Inject
//    lateinit var activityRequiredSet: Set<@JvmSuppressWildcards ActivityRequired>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navComponentRouter.onCreate(this)

        navComponentRouter.setNavGraph()
    }

    override fun onStart() {
        Log.d("MainActivity", "onStart")
        super.onStart()
    }

    override fun onStop() {
        Log.d("MainActivity", "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("MainActivity", "onDestroy")
        super.onDestroy()
    }
}