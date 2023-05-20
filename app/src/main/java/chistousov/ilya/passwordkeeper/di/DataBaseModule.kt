package chistousov.ilya.passwordkeeper.di

import android.content.Context
import androidx.room.Room
import chistousov.ilya.passwordkeeper.data.db.PasswordDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context) : PasswordDb {
        return Room.databaseBuilder(
            context, PasswordDb::class.java, DB_NAME
        ).build()
    }

    @Provides
    fun providePasswordDao(passwordDb: PasswordDb) = passwordDb.getPasswordDao()

    companion object {
        private const val DB_NAME = "password_db"
    }
}