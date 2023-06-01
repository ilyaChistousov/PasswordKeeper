package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.usecase.CreatePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.DeletePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GenerateUniquePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GetPasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.UpdatePasswordUseCase
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.Validator
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
        validatePasswordAndTitle(password, title)
        if (_passwordValidation.value.entries.all { it.value == null }) {
            val newPassword = PasswordModel(
                title = title.trim(),
                password = password.trim(),
                login = login.trim(),
                email = email.trim(),
                url = url.trim()
            )
            viewModelScope.launch {
                createPasswordUseCase(newPassword)
                onSuccess()
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
        validatePasswordAndTitle(password, title)
        if (_passwordValidation.value.entries.all { it.value == null }) {
            val updatedPassword = PasswordModel(
                id,
                title.trim(),
                password.trim(),
                login.trim(),
                email.trim(),
                url.trim()
            )
            viewModelScope.launch {
                updatePasswordUseCase(updatedPassword)
                onSuccess()
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

    private fun validatePasswordAndTitle(password: String, title: String) {
        val validator = Validator()

        val validatedPassword = validator.validate(password)
        val validatedTitle = validator.validate(title)

        val map = mutableMapOf<String, Int?>()
        map[Validator.PASSWORD_KEY] = validatedPassword
        map[Validator.TITLE_KEY] = validatedTitle
        _passwordValidation.value = map
    }
}