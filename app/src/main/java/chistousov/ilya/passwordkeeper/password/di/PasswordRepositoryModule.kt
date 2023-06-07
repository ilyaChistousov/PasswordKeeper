package chistousov.ilya.passwordkeeper.password.di

import chistousov.ilya.password_details.domain.repository.PasswordRepository
import chistousov.ilya.passwordkeeper.password.repository.AdapterPasswordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface PasswordRepositoryModule {

    @Binds
    fun bindPasswordRepository(passwordRepository: AdapterPasswordRepository): PasswordRepository
}