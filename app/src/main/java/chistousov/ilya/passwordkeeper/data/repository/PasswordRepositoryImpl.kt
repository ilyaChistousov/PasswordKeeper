package chistousov.ilya.passwordkeeper.data.repository

import chistousov.ilya.passwordkeeper.data.dao.PasswordDao
import chistousov.ilya.passwordkeeper.data.mapper.PasswordMapper
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val passwordDao: PasswordDao,
    private val passwordMapper: PasswordMapper
) : PasswordRepository {

    override suspend fun getPassword(passwordId: Int): PasswordModel {
        return passwordMapper.mapFromDbEntityToModel(passwordDao.getPassword(passwordId))
    }

    override fun getListPassword(): Flow<List<PasswordModel>> {
        return passwordDao.getPasswordList().map {
            passwordMapper.mapFromDbEntityListToModelList(it)
        }
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