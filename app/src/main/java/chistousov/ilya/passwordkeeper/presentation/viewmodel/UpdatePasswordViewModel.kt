package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.exception.ValidationException
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.usecase.DeletePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GetPasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.UpdatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val getPasswordUseCase: GetPasswordUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase
) : ViewModel() {

    private val _updatePasswordState = MutableStateFlow(State())
    val updatePasswordState: StateFlow<State> = _updatePasswordState

    private val _validatingFields = MutableStateFlow(emptyMap<String, Int?>())
    val validatingFields: StateFlow<Map<String, Int?>> = _validatingFields.asStateFlow()

    fun getPassword(id: Int) = viewModelScope.launch {
        _updatePasswordState.update {
            it.copy(
                selectedPassword = getPasswordUseCase(id).unwrap(),
                isLoaded = true
            )
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

    data class State(
        val selectedPassword: PasswordModel? = null,
        val isLoaded: Boolean = false
    )
}