package chistousov.ilya.passwordkeeper.navigation.di

import chistousov.ilya.navigation.presentation.navigation.DestinationProvider
import chistousov.ilya.passwordkeeper.navigation.DefaultDestinationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DestinationProviderModule {

    @Binds
    fun bindDestinationProvider(destinationProvider: DefaultDestinationProvider): DestinationProvider
}