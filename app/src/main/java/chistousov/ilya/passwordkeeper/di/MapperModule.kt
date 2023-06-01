package chistousov.ilya.passwordkeeper.di

import chistousov.ilya.passwordkeeper.data.mapper.DefaultPasswordMapper
import chistousov.ilya.passwordkeeper.data.mapper.DefaultUserMapper
import chistousov.ilya.passwordkeeper.data.mapper.PasswordMapper
import chistousov.ilya.passwordkeeper.data.mapper.UserMapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MapperModule {

    @Singleton
    @Binds
    fun bindPasswordMapper(impl: DefaultPasswordMapper): PasswordMapper

    @Singleton
    @Binds
    fun bindUserMapper(impl: DefaultUserMapper): UserMapper
}