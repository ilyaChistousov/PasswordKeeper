package chistousov.ilya.navigation.presentation.navigation

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes

interface DestinationProvider {

    @IdRes
    fun provideStartDestination(): Int

    @NavigationRes
    fun provideNavigationGraphId(): Int

    @IdRes
    fun provideSignUpDestination(): Int

    @IdRes
    fun providePasswordListDestination(): Int
}