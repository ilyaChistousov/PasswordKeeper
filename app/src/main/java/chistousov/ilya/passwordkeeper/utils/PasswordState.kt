package chistousov.ilya.passwordkeeper.utils

sealed class PasswordState<T> {

    class Loading<T> : PasswordState<T>()
    class Success<T>(val value: T) : PasswordState<T>()
    class Error<T>(val message: Int) : PasswordState<T>()
}