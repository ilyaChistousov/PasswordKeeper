package chistousov.ilya.passwordkeeper.password.di

import chistousov.ilya.password_details.presentation.PasswordRouter
import chistousov.ilya.passwordkeeper.password.AdapterPasswordRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface PasswordRouterModule {

    @Binds
    fun bindPasswordRouter(passwordRouter: AdapterPasswordRouter): PasswordRouter
}