package chistousov.ilya.passwordkeeper.di

import android.content.Context
import androidx.room.Room
import chistousov.ilya.passwordkeeper.data.dao.PasswordDao
import chistousov.ilya.passwordkeeper.data.db.PasswordDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) : PasswordDb {
        return Room.databaseBuilder(
            context, PasswordDb::class.java, "password_db"
        ).build()
    }

    @Singleton
    @Provides
    fun providePasswordDao(passwordDb: PasswordDb) : PasswordDao {
        return passwordDb.getPasswordDao()
    }


}