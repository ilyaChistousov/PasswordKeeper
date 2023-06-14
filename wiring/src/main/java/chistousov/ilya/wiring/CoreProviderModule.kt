package chistousov.ilya.wiring

import android.content.Context
import chistousov.ilya.common.CommonUi
import chistousov.ilya.common.Core
import chistousov.ilya.common.CoreProvider
import chistousov.ilya.common.Logger
import chistousov.ilya.common.Resource
import chistousov.ilya.common_impl.ActivityRequired
import chistousov.ilya.common_impl.DefaultCoreProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.ElementsIntoSet

@Module
@InstallIn(SingletonComponent::class)
class CoreProviderModule {

    @Provides
    fun provideCoreProvider(
        @ApplicationContext appContext: Context
    ): CoreProvider {
        return DefaultCoreProvider(appContext)
    }

    @Provides
    @ElementsIntoSet
    fun provideActivityRequired(
        commonUi: CommonUi
    ): Set<@JvmSuppressWildcards ActivityRequired> {
        val set = hashSetOf<ActivityRequired>()
        if (commonUi is ActivityRequired) set.add(commonUi)
        return set
    }

    @Provides
    fun provideLogger(): Logger {
        return Core.logger
    }

    @Provides
    fun provideResource(): Resource {
        return Core.resource
    }

    @Provides
    fun provideCommonUi(): CommonUi {
        return Core.commonUi
    }
}