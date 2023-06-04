package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.exception.ValidationException
import chistousov.ilya.passwordkeeper.domain.usecase.CreatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    private val createPasswordUseCase: CreatePasswordUseCase
) : ViewModel() {

    private val _validatingFields = MutableStateFlow<Map<String, Int?>>(emptyMap())
    val validatingFields: StateFlow<Map<String, Int?>> = _validatingFields.asStateFlow()

    fun createPassword(
        title: String,
        password: String,
        login: String,
        email: String,
        url: String,
        onSuccess: () -> Unit
    ) = viewModelScope.launch{
        try {
            createPasswordUseCase(title, password, login, email, url)
            onSuccess()
        } catch (e: ValidationException) {
            _validatingFields.value = e.validationMap
        }
    }
}