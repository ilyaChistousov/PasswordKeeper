package chistousov.ilya.sign_up.domain.repository

import chistousov.ilya.sign_up.domain.entity.AccountModel

interface SignUpRepository {

    suspend fun signUp(accountModel: AccountModel)
}