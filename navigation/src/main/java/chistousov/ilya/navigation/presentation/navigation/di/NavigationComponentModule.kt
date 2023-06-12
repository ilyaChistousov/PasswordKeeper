package chistousov.ilya.navigation.presentation.navigation.di

import chistousov.ilya.common_impl.ActivityRequired
import chistousov.ilya.navigation.presentation.navigation.NavComponentRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
class NavigationComponentModule {

    @Provides
    @IntoSet
    fun provideNavComponentRouterAsRequireActivity(
        navComponentRouter: NavComponentRouter
    ): ActivityRequired {
        return navComponentRouter
    }
}