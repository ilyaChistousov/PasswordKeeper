package chistousov.ilya.passwordkeeper.signup.repository

import chistousov.ilya.data.account.AccountDataRepository
import chistousov.ilya.passwordkeeper.signup.mapper.AccountMapper
import chistousov.ilya.sign_up.domain.entity.AccountModel
import chistousov.ilya.sign_up.domain.repository.SignUpRepository
import javax.inject.Inject

class AdapterSignUpRepository @Inject constructor(
    private val accountDataRepository: AccountDataRepository,
    private val accountMapper: AccountMapper
) : SignUpRepository {
    override suspend fun signUp(accountModel: AccountModel) {
        accountDataRepository.signUp(accountMapper.reverseMap(accountModel))
    }

    override suspend fun isSignedUp(): Boolean {
        return accountDataRepository.isSignedUp()
    }
}