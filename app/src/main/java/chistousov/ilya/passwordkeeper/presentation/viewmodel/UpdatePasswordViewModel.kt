package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.exception.ValidationException
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.usecase.DeletePasswordUseCase
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
class UpdatePasswordViewModel @Inject constructor(
    private val getPasswordUseCase: GetPasswordUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase
) : ViewModel() {

    private val _validatingFields = MutableStateFlow<Map<String, Int?>>(emptyMap())
    val validatingFields = _validatingFields.asStateFlow()

    private val _selectedPassword = MutableStateFlow<UiState<PasswordModel>>(UiState.Loading())
    val selectedPassword: StateFlow<UiState<PasswordModel>> = _selectedPassword

    fun getPassword(id: Int) = viewModelScope.launch {
        _selectedPassword.value = getPasswordUseCase(id).mapToUiState()
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
                _validatingFields.value = e.validationMap
            }
        }
    }

    fun deletePassword(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            deletePasswordUseCase(id)
            onSuccess()
        }
    }
}