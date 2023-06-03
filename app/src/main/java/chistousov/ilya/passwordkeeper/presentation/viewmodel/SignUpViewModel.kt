package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.exception.ValidationException
import chistousov.ilya.passwordkeeper.domain.usecase.IsSignedUpUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val isSignedUpUseCase: IsSignedUpUseCase
) : ViewModel() {

    private val _signUpState = MutableStateFlow(State())
    val signUpState: StateFlow<State> = _signUpState.asStateFlow()

    fun checkRegistration() = viewModelScope.launch {
        _signUpState.update { it.copy(isSignedUp = isSignedUpUseCase().unwrap(), isLoaded = true) }
    }

    fun signUp(password: String, confirmPassword: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                signUpUseCase(password, confirmPassword)
                onSuccess()
            } catch (e: ValidationException) {
                _signUpState.update { it.copy(validationMap = e.validationMap) }
            }
        }
    }

    data class State(
        val isSignedUp: Boolean = false,
        val validationMap: Map<String, Int?> = emptyMap(),
        val isLoaded: Boolean = false
    )
}
