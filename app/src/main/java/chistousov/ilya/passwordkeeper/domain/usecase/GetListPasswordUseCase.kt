package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository

class GetListPasswordUseCase (private val repository: PasswordRepository) {

    operator fun invoke() = repository.getListPassword()
}