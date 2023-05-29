package chistousov.ilya.passwordkeeper.data.repository

import chistousov.ilya.passwordkeeper.data.dao.PasswordDao
import chistousov.ilya.passwordkeeper.data.mapper.PasswordMapper
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao,
    private val passwordMapper: PasswordMapper,
) : PasswordRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getPassword(passwordId: Int): PasswordModel = withContext(ioDispatcher) {
        return@withContext passwordMapper.mapFromDbEntityToModel(passwordDao.getPassword(passwordId))
    }

    override suspend fun getListPassword(): List<PasswordModel> = withContext(ioDispatcher) {
        return@withContext passwordMapper.mapFromDbEntityListToModelList(passwordDao.getPasswordList())
    }

    override suspend fun createPassword(passwordModel: PasswordModel) = withContext(ioDispatcher) {
        passwordDao.insertPassword(passwordMapper.mapFromModelToDbEntity(passwordModel))
    }

    override suspend fun updatePassword(passwordModel: PasswordModel) = withContext(ioDispatcher) {
        passwordDao.insertPassword(passwordMapper.mapFromModelToDbEntity(passwordModel))
    }

    override suspend fun deletePassword(passwordId: Int) = withContext(ioDispatcher) {
        passwordDao.deletePassword(passwordId)
    }

    override suspend fun searchPassword(query: String): List<PasswordModel> = withContext(ioDispatcher) {
        return@withContext passwordMapper.mapFromDbEntityListToModelList(passwordDao.searchPassword(query))
    }
}