package chistousov.ilya.passwordkeeper.password.di

import chistousov.ilya.passwordkeeper.password.mapper.DefaultPasswordMapper
import chistousov.ilya.passwordkeeper.password.mapper.PasswordMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface PasswordMapperModule {

    @Binds
    fun bindMapper(mapper: DefaultPasswordMapper): PasswordMapper
}