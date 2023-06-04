package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.exception.ValidationException
import chistousov.ilya.passwordkeeper.domain.usecase.IsSignedUpUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.SignUpUseCase
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val isSignedUpUseCase: IsSignedUpUseCase
) : ViewModel() {

    private val _passwordValidation = MutableStateFlow<Map<String, Int?>>(mutableMapOf())
    val passwordValidation: StateFlow<Map<String, Int?>> = _passwordValidation.asStateFlow()
    private val _isSignedUp = MutableStateFlow<UiState<Boolean>>(UiState.Loading())
    val isSignedUp: StateFlow<UiState<Boolean>> = _isSignedUp.asStateFlow()

    fun checkRegistration() = viewModelScope.launch {
        _isSignedUp.value = isSignedUpUseCase().mapToUiState()
    }

    fun signUp(password: String, confirmPassword: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                signUpUseCase(password, confirmPassword)
                onSuccess()
            } catch (e: ValidationException) {
                _passwordValidation.value = e.validationMap
            }
        }
    }
}
