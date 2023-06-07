package chistousov.ilya.passwordkeeper.signup.di

import chistousov.ilya.passwordkeeper.signup.repository.AdapterSignUpRepository
import chistousov.ilya.sign_up.domain.repository.SignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SignUpRepositoryModule {

    @Binds
    fun bindSignUpRepository(signUpRepository: AdapterSignUpRepository): SignUpRepository
}