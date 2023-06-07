package chistousov.ilya.passwordkeeper.password.repository

import chistousov.ilya.data.password.PasswordDataRepository
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.repository.PasswordRepository
import chistousov.ilya.passwordkeeper.password.mapper.PasswordMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AdapterPasswordRepository @Inject constructor(
    private val passwordDataRepository: PasswordDataRepository,
    private val passwordMapper: PasswordMapper
) : PasswordRepository {
    override fun getAllPasswords(): Flow<List<PasswordModel>> {
        return passwordDataRepository.getAllPasswords().map {
            it.map(passwordMapper::map)
        }
    }

    override fun searchPassword(query: String): Flow<List<PasswordModel>> {
        return passwordDataRepository.searchPassword(query).map {
            it.map(passwordMapper::map)
        }
    }

    override suspend fun getPassword(passwordId: Int): PasswordModel {
        return passwordMapper.map(
            passwordDataRepository.getPassword(passwordId)
        )
    }

    override suspend fun deletePassword(id: Int) {
        passwordDataRepository.deletePassword(id)
    }

    override suspend fun createPassword(passwordModel: PasswordModel) {
        passwordDataRepository.createPassword(passwordMapper.reverseMap(passwordModel))
    }

    override suspend fun updatePassword(passwordModel: PasswordModel) {
        passwordDataRepository.updatePassword(passwordMapper.reverseMap(passwordModel))
    }
}