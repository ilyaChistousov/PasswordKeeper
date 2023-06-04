package chistousov.ilya.passwordkeeper.domain.repository

import chistousov.ilya.passwordkeeper.domain.model.UserModel

interface UserRepository {

    suspend fun createUser(userModel: UserModel)

    suspend fun signIn(password: String)

    suspend fun isRegistered(): Boolean

    suspend fun deleteUser()
}