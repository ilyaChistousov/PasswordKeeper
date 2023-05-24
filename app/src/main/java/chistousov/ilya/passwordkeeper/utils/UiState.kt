package chistousov.ilya.passwordkeeper.utils

sealed class UiState<T> {

    class Loading<T> : UiState<T>()
    class Success<T>(val value: T) : UiState<T>()
    class Error<T>(val message: Int) : UiState<T>()
}