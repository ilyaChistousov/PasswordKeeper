package chistousov.ilya.passwordkeeper.domain.usecase

import chistousov.ilya.passwordkeeper.domain.entity.PasswordEntity
import chistousov.ilya.passwordkeeper.domain.repository.PasswordRepository

class CreatePasswordUseCase (private val repository: PasswordRepository) {

    suspend operator fun invoke(passwordEntity: PasswordEntity) =
        repository.createPassword(passwordEntity)
}