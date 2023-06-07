package chistousov.ilya.passwordkeeper.signup.di

import chistousov.ilya.passwordkeeper.signup.AdapterSignUpRouter
import chistousov.ilya.sign_up.presentation.SignUpRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SignUpRouterModule {

    @Binds
    fun bindSignUpRouter(signUpRouter: AdapterSignUpRouter): SignUpRouter
}