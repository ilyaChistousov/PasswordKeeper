package chistousov.ilya.sign_in.domain.repository

interface SignInRepository {

    suspend fun signIn(password: String)
}