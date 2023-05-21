package chistousov.ilya.passwordkeeper.data.repository

import chistousov.ilya.passwordkeeper.data.dao.PasswordDao
import chistousov.ilya.passwordkeeper.data.mapper.PasswordMapper
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao,
    private val passwordMapper: PasswordMapper
) : PasswordRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    override suspend fun getPassword(passwordId: Int): PasswordModel {
        return passwordMapper.mapFromDbEntityToModel(passwordDao.getPassword(passwordId))
    }

    override fun getListPassword(): Flow<List<PasswordModel>> {
        return passwordDao.getPasswordList().map {
            passwordMapper.mapFromDbEntityListToModelList(it)
        }.stateIn(coroutineScope, SharingStarted.Lazily, emptyList())
    }

    override suspend fun createPassword(passwordModel: PasswordModel) {
        passwordDao.insertPassword(passwordMapper.mapFromModelToDbEntity(passwordModel))
    }

    override suspend fun updatePassword(passwordModel: PasswordModel) {
        passwordDao.insertPassword(passwordMapper.mapFromModelToDbEntity(passwordModel))
    }

    override suspend fun deletePassword(passwordId: Int) {
        passwordDao.deletePassword(passwordId)
    }
}