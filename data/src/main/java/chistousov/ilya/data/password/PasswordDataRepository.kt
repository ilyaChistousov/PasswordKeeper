package chistousov.ilya.data.password

import chistousov.ilya.data.password.dao.PasswordDao
import chistousov.ilya.data.password.entity.PasswordDataEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordDataRepository @Inject constructor(
    private val passwordDao: PasswordDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getPassword(passwordId: Int): PasswordDataEntity = withContext(ioDispatcher) {
        return@withContext passwordDao.getPassword(passwordId)
    }

    fun getAllPasswords(): Flow<List<PasswordDataEntity>> {
        return passwordDao.getPasswordList()
    }

    suspend fun createPassword(passwordDataEntity: PasswordDataEntity) = withContext(ioDispatcher) {
        passwordDao.insertPassword(passwordDataEntity)
    }

    suspend fun updatePassword(passwordDataEntity: PasswordDataEntity) = withContext(ioDispatcher) {
        passwordDao.insertPassword(passwordDataEntity)
    }

    suspend fun deletePassword(passwordId: Int) = withContext(ioDispatcher) {
        passwordDao.deletePassword(passwordId)
    }

    fun searchPassword(query: String): Flow<List<PasswordDataEntity>> {
        return passwordDao.searchPassword(query)
    }
}