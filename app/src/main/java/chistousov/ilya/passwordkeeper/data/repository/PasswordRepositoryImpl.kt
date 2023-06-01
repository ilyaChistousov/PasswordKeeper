package chistousov.ilya.passwordkeeper.data.repository

import chistousov.ilya.passwordkeeper.data.dao.PasswordDao
import chistousov.ilya.passwordkeeper.data.mapper.PasswordMapper
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao,
    private val passwordMapper: PasswordMapper,
    private val ioDispatcher: CoroutineDispatcher
) : PasswordRepository {

    override suspend fun getPassword(passwordId: Int): PasswordModel = withContext(ioDispatcher) {
        return@withContext passwordMapper.map(passwordDao.getPassword(passwordId))
    }

    override fun getListPassword(): Flow<List<PasswordModel>> {
        return passwordDao.getPasswordList()
            .map { passwordMapper.mapList(it) }
            .flowOn(ioDispatcher)
    }

    override suspend fun createPassword(passwordModel: PasswordModel) = withContext(ioDispatcher) {
        passwordDao.insertPassword(passwordMapper.reverseMap(passwordModel))
    }

    override suspend fun updatePassword(passwordModel: PasswordModel) = withContext(ioDispatcher) {
        passwordDao.insertPassword(passwordMapper.reverseMap(passwordModel))
    }

    override suspend fun deletePassword(passwordId: Int) = withContext(ioDispatcher) {
        passwordDao.deletePassword(passwordId)
    }

    override fun searchPassword(query: String): Flow<List<PasswordModel>> {
        return passwordDao.searchPassword(query)
            .map { passwordMapper.mapList(it) }
            .flowOn(ioDispatcher)
    }
}