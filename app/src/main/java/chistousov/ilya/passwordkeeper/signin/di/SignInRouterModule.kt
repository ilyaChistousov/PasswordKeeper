package chistousov.ilya.passwordkeeper.signin.di

import chistousov.ilya.passwordkeeper.signin.AdapterSignInRouter
import chistousov.ilya.sign_in.presentation.SignInRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SignInRouterModule {

    @Binds
    fun bindSignInRouter(signInRouter: AdapterSignInRouter): SignInRouter
}