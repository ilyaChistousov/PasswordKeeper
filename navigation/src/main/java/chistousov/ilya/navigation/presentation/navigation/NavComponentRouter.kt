package chistousov.ilya.navigation.presentation.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import chistousov.ilya.navigation.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavComponentRouter @Inject constructor(
    private val destinationProvider: DestinationProvider
) {

    private var activity: FragmentActivity? = null

    var currentDestination = 0
        private set

    fun onCreate(activity: FragmentActivity) {
        this.activity = activity
    }

    fun setNavGraph() {
        val graph = getNavController().navInflater.inflate(
            destinationProvider.provideNavigationGraphId()
        )
        getNavController().graph = graph
    }

    private fun getNavController(): NavController {
        val fragmentManager = activity?.supportFragmentManager
        val navHost = fragmentManager?.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }

    fun launch(destinationId: Int, args: Bundle? = null) {
        if (args == null) {
            getNavController().navigate(destinationId)
        } else {
            getNavController().navigate(destinationId, args)
        }
        currentDestination = destinationId
    }

    fun pop() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun requireActivity(): FragmentActivity {
        return activity!!
    }
}