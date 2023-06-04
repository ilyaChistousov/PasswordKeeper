package chistousov.ilya.passwordkeeper.presentation.utils

import chistousov.ilya.passwordkeeper.domain.Result

sealed class UiState<T>  {
    class Loading<T> : UiState<T>()
    class Success<T>(val value: T) : UiState<T>()
    class Error<T>(val message: String) : UiState<T>()
}

fun<T> Result<T>.mapToUiState(): UiState<T> {
    return when (this) {
        is Result.Success -> {
            UiState.Success(value)
        }
        is Result.Error -> {
            UiState.Error(message)
        }
    }
}