package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import javax.inject.Inject

class GetListPasswordUseCase @Inject constructor (private val repository: PasswordRepository) {

    operator fun invoke() = repository.getListPassword()
}