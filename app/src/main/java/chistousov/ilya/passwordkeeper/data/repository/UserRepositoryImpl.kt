package chistousov.ilya.passwordkeeper.data.repository

import chistousov.ilya.passwordkeeper.data.dao.UserDao
import chistousov.ilya.passwordkeeper.data.mapper.UserMapper
import chistousov.ilya.passwordkeeper.domain.exception.UserNotFoundException
import chistousov.ilya.passwordkeeper.domain.model.UserModel
import chistousov.ilya.passwordkeeper.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher,
    private val mapper: UserMapper
) : UserRepository {

    override suspend fun createUser(userModel: UserModel) = withContext(ioDispatcher) {
        userDao.createUser(mapper.reverseMap(userModel))
    }

    override suspend fun signIn(password: String): Unit = withContext(ioDispatcher) {
        try {
            userDao.getUser(password).toString()
        } catch (e: NullPointerException) {
            throw UserNotFoundException("Неправильный пароль")
        }
    }

    override suspend fun isRegistered(): Boolean = withContext(ioDispatcher) {
        return@withContext userDao.isRegistered() != null
    }

    override suspend fun deleteUser() {
        TODO("Not yet implemented")
    }
}