package chistousov.ilya.navigation.presentation.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import chistousov.ilya.common_impl.ActivityRequired
import chistousov.ilya.navigation.R
import chistousov.ilya.presentation.SCREEN_ARGS
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavComponentRouter @Inject constructor(
    private val destinationProvider: DestinationProvider
) : ActivityRequired {

    private var activity: FragmentActivity? = null

    var currentDestination = 0
        private set

    override fun onCreate(activity: FragmentActivity) {
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

    fun launch(destinationId: Int, args: Serializable? = null) {
        if (args == null) {
            getNavController().navigate(destinationId)
        } else {
            val args = bundleOf(SCREEN_ARGS to args)
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