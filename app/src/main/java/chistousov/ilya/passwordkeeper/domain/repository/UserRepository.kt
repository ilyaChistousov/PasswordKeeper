package chistousov.ilya.passwordkeeper.domain.repository

import chistousov.ilya.passwordkeeper.domain.model.UserModel

interface UserRepository {

    suspend fun createUser(userModel: UserModel)

    suspend fun getUser(password: String): UserModel

    suspend fun isRegistered(): Boolean

    suspend fun deleteUser()
}