package chistousov.ilya.passwordkeeper.signin.repository

import chistousov.ilya.data.account.AccountDataRepository
import chistousov.ilya.sign_in.domain.repository.SignInRepository
import javax.inject.Inject

class AdapterSignInRepository @Inject constructor(
    private val accountDataRepository: AccountDataRepository
) : SignInRepository {

    override suspend fun signIn(password: String) {
        accountDataRepository.signIn(password)
    }
}