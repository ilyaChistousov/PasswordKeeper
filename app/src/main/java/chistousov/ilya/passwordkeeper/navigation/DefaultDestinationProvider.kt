package chistousov.ilya.passwordkeeper.navigation

import chistousov.ilya.navigation.presentation.navigation.DestinationProvider
import chistousov.ilya.passwordkeeper.R
import javax.inject.Inject

class DefaultDestinationProvider @Inject constructor() : DestinationProvider {

    override fun provideStartDestination(): Int {
        return R.id.signInFragment
    }

    override fun provideNavigationGraphId(): Int {
        return R.navigation.nav_graph
    }

    override fun provideSignUpDestination(): Int {
        return R.id.signUpFragment
    }


    override fun providePasswordListDestination(): Int {
        return R.id.passwordListFragment
    }
}