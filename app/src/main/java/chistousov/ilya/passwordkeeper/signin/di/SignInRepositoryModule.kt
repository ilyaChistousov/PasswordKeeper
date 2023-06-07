package chistousov.ilya.passwordkeeper.signin.di

import chistousov.ilya.passwordkeeper.signin.repository.AdapterSignInRepository
import chistousov.ilya.sign_in.domain.repository.SignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SignInRepositoryModule {

    @Binds
    fun bindSignInRepository(signInRepository: AdapterSignInRepository): SignInRepository
}