package chistousov.ilya.passwordkeeper.signup.di

import chistousov.ilya.passwordkeeper.signup.mapper.AccountMapper
import chistousov.ilya.passwordkeeper.signup.mapper.DefaultAccountMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface SignUpMapperModule {

    @Binds
    fun bindMapper(mapper: DefaultAccountMapper): AccountMapper
}