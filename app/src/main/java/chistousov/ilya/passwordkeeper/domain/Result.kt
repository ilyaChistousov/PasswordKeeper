package chistousov.ilya.passwordkeeper.domain

sealed class Result<T> {

    data class Success<T>(val value: T) : Result<T>()
    data class Error<T>(val resId: Int): Result<T>()
}