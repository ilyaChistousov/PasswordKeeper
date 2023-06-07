package chistousov.ilya.data.account

import chistousov.ilya.common.UserNotFoundException
import chistousov.ilya.data.account.dao.AccountDao
import chistousov.ilya.data.account.entity.AccountDataEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountDataRepository @Inject constructor(
    private val accountDao: AccountDao,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun signUp(userModel: AccountDataEntity) = withContext(ioDispatcher) {
        accountDao.createAccount(userModel)
    }

    suspend fun signIn(password: String): Unit = withContext(ioDispatcher) {
        try {
            accountDao.getAccount(password).toString()
        } catch (e: NullPointerException) {
            throw UserNotFoundException()
        }
    }

    suspend fun isSignedUp(): Boolean = withContext(ioDispatcher) {
        return@withContext accountDao.isRegistered() != null
    }

    suspend fun deleteUser() {
        TODO("Not yet implemented")
    }
}