package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.model.UserModel
import chistousov.ilya.passwordkeeper.domain.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repository: UserRepository) {

    suspend operator fun invoke(password: String) {
        val user = UserModel(password)

        repository.createUser(user)
    }
}