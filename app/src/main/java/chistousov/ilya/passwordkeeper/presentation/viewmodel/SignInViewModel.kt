package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.exception.UserNotFoundException
import chistousov.ilya.passwordkeeper.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _errorMessage = MutableStateFlow(State())
    val errorMessage: StateFlow<State> = _errorMessage.asStateFlow()

    fun signIn(password: String, onSuccess: () -> Unit) = viewModelScope.launch {
        try {
            signInUseCase(password)
            onSuccess()
        } catch (e: UserNotFoundException) {
            _errorMessage.update { it.copy(errorMessage = e.message) }
        }
    }

    data class State(
        val errorMessage: String = ""
    )
}