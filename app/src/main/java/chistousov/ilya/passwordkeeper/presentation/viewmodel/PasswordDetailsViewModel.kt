package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.exception.ValidationException
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.usecase.CreatePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.DeletePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GenerateUniquePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GetPasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.UpdatePasswordUseCase
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordDetailsViewModel @Inject constructor(
    private val createPasswordUseCase: CreatePasswordUseCase,
    private val getPasswordUseCase: GetPasswordUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val generateUniquePasswordUseCase: GenerateUniquePasswordUseCase
) : ViewModel() {

    private val _passwordValidation =
        MutableStateFlow<Map<String, Int?>>(mutableMapOf())
    val passwordValidation: StateFlow<Map<String, Int?>> =
        _passwordValidation.asStateFlow()

    private val _selectedPassword = MutableStateFlow<UiState<PasswordModel>>(UiState.Loading())
    val selectedPassword: StateFlow<UiState<PasswordModel>> = _selectedPassword

    private val _generatedPassword = MutableStateFlow("")
    val generatedPassword: StateFlow<String> = _generatedPassword

    fun getPassword(id: Int) = viewModelScope.launch {
        _selectedPassword.value = getPasswordUseCase(id).mapToUiState()
    }

    fun createPassword(
        title: String,
        password: String,
        login: String,
        email: String,
        url: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                createPasswordUseCase(title, password, login, email, url)
                onSuccess()
            } catch (e: ValidationException) {
                _passwordValidation.value = e.validationMap
            }
        }
    }

    fun updatePassword(
        id: Int,
        title: String,
        password: String,
        login: String,
        email: String,
        url: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                updatePasswordUseCase(id, title, password, login, email, url)
                onSuccess()
            } catch (e: ValidationException) {
                _passwordValidation.value = e.validationMap
            }
        }
    }

    fun deletePassword(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            deletePasswordUseCase(id)
            onSuccess()
        }
    }

    fun generatePassword(
        length: Int,
        withDigits: Boolean,
        withUppercase: Boolean,
        withSpecial: Boolean
    ) {
        _generatedPassword.value =
            generateUniquePasswordUseCase(length, withDigits, withUppercase, withSpecial)
    }
}