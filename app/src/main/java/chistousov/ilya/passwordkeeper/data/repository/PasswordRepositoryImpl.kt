package chistousov.ilya.passwordkeeper.data.repository

import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.data.dao.PasswordDao
import chistousov.ilya.passwordkeeper.data.mapper.PasswordMapper
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import chistousov.ilya.passwordkeeper.utils.PasswordState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao,
    private val passwordMapper: PasswordMapper,
) : PasswordRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getPassword(passwordId: Int): Flow<PasswordState<PasswordModel>> = flow {
        delay(1000)
        try {
            val password = passwordDao.getPassword(passwordId)
            val mappedPassword = passwordMapper.mapFromDbEntityToModel(password)
            emit(PasswordState.Success(mappedPassword))
        } catch (e: Exception) {
            emit(PasswordState.Error(R.string.error_password_not_found))
        }
    }.flowOn(ioDispatcher)

    override fun getListPassword(): Flow<List<PasswordModel>>  {
        return passwordDao.getPasswordList().map {
            passwordMapper.mapFromDbEntityListToModelList(it)
        }.flowOn(ioDispatcher)
    }

    override suspend fun createPassword(passwordModel: PasswordModel) = withContext(ioDispatcher){
        passwordDao.insertPassword(passwordMapper.mapFromModelToDbEntity(passwordModel))
    }

    override suspend fun updatePassword(passwordModel: PasswordModel) = withContext(ioDispatcher){
        passwordDao.insertPassword(passwordMapper.mapFromModelToDbEntity(passwordModel))
    }

    override suspend fun deletePassword(passwordId: Int) = withContext(ioDispatcher){
        passwordDao.deletePassword(passwordId)
    }

    override suspend fun searchPassword(query: String): Flow<List<PasswordModel>> {
        return passwordDao.searchPassword(query).map {
            passwordMapper.mapFromDbEntityListToModelList(it)
        }.flowOn(ioDispatcher)
    }
}