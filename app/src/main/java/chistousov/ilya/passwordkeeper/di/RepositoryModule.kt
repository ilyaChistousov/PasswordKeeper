package chistousov.ilya.passwordkeeper.di

import chistousov.ilya.passwordkeeper.data.repository.PasswordRepositoryImpl
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindPasswordRepository(impl: PasswordRepositoryImpl): PasswordRepository
}