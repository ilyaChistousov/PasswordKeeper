package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository
import javax.inject.Inject

class SearchPasswordUseCase @Inject constructor(private val repository: PasswordRepository) {

    suspend operator fun invoke(query: String) = repository.searchPassword(query)
}