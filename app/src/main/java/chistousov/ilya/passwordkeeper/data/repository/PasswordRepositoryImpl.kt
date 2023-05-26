package chistousov.ilya.passwordkeeper.data.repository

import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.passwordkeeper.data.dao.PasswordDao
import chistousov.ilya.passwordkeeper.data.mapper.PasswordMapper
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import chistousov.ilya.passwordkeeper.utils.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao,
    private val passwordMapper: PasswordMapper,
) : PasswordRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getPassword(passwordId: Int): Flow<UiState<PasswordModel>> = flow {
        delay(1000)
        try {
            val password = passwordDao.getPassword(passwordId)
            val mappedPassword = passwordMapper.mapFromDbEntityToModel(password)
            emit(UiState.Success(mappedPassword))
        } catch (e: Exception) {
            emit(UiState.Error(R.string.error_password_not_found))
        }
    }.flowOn(ioDispatcher)

    override fun getListPassword(): Flow<UiState<List<PasswordModel>>> = flow {
        emit(UiState.Loading())
        delay(1000)
        try {
            val passwordList = passwordDao.getPasswordList()
            val mappedList = passwordMapper.mapFromDbEntityListToModelList(passwordList)
            emit(UiState.Success(mappedList))
        } catch (e: Exception) {
            emit(UiState.Error(R.string.error_loading_failed))
        }
    }.flowOn(ioDispatcher)

    override suspend fun createPassword(passwordModel: PasswordModel) = withContext(ioDispatcher){
        passwordDao.insertPassword(passwordMapper.mapFromModelToDbEntity(passwordModel))
    }

    override suspend fun updatePassword(passwordModel: PasswordModel) = withContext(ioDispatcher){
        passwordDao.insertPassword(passwordMapper.mapFromModelToDbEntity(passwordModel))
    }

    override suspend fun deletePassword(passwordId: Int) = withContext(ioDispatcher){
        passwordDao.deletePassword(passwordId)
    }

    override suspend fun searchPassword(query: String): Flow<UiState<List<PasswordModel>>> = flow {
        emit(UiState.Loading())
        delay(1000)
        try {
            val passwordList = passwordDao.searchPassword(query)
            val mappedList = passwordMapper.mapFromDbEntityListToModelList(passwordList)
            emit(UiState.Success(mappedList))
        } catch (e: Exception) {
            emit(UiState.Error(R.string.error_loading_failed))
        }
    }.flowOn(ioDispatcher)
}