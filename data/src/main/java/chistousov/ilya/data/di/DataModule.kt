package chistousov.ilya.data.di

import android.content.Context
import androidx.room.Room
import chistousov.ilya.data.account.dao.AccountDao
import chistousov.ilya.data.db.PasswordDb
import chistousov.ilya.data.password.dao.PasswordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): PasswordDb {
        return Room.databaseBuilder(
            context, PasswordDb::class.java, "password_db"
        ).build()
    }

    @Singleton
    @Provides
    fun providePasswordDao(passwordDb: PasswordDb): PasswordDao {
        return passwordDb.getPasswordDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(passwordDb: PasswordDb): AccountDao {
        return passwordDb.getUserDao()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}