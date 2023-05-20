package chistousov.ilya.passwordkeeper.domain.repository

import chistousov.ilya.passwordkeeper.domain.entity.PasswordEntity
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {

    suspend fun getPassword(passwordId: Int) : PasswordEntity

    fun getListPassword() : Flow<List<PasswordEntity>>

    suspend fun createPassword(passwordEntity: PasswordEntity)

    suspend fun updatePassword(passwordEntity: PasswordEntity)

    suspend fun deletePassword(passwordId: Int)

}