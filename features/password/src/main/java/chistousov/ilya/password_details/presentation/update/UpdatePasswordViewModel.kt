package chistousov.ilya.password_details.presentation.update

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.exceptions.EmptyPasswordException
import chistousov.ilya.password_details.domain.exceptions.EmptyTitleException
import chistousov.ilya.password_details.domain.usecases.DeletePasswordUseCase
import chistousov.ilya.password_details.domain.usecases.GetPasswordUseCase
import chistousov.ilya.password_details.domain.usecases.UpdatePasswordUseCase
import chistousov.ilya.password_details.presentation.PasswordRouter
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
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val router: PasswordRouter
) : ViewModel() {

    private val _updatePasswordState = MutableStateFlow(State())
    val updatePasswordState: StateFlow<State> = _updatePasswordState

    private val _validatingFields = MutableStateFlow(emptyMap<String, Int?>())
    val validatingFields: StateFlow<Map<String, Int?>> = _validatingFields.asStateFlow()

    fun getPassword(id: Int) = viewModelScope.launch {
        _updatePasswordState.update {
            it.copy(
                selectedPasswordModel = getPasswordUseCase(id).unwrap(),
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
        url: String
    ) {
        viewModelScope.launch {
            try {
                updatePasswordUseCase(id, title, password, login, email, url)
                router.goBack()
            } catch (e: EmptyTitleException) { }
            catch (e: EmptyPasswordException) { }
        }
    }

    fun deletePassword(id: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            deletePasswordUseCase(id)
            onSuccess()
        }
    }

    data class State(
        val selectedPasswordModel: PasswordModel? = null,
        val isLoaded: Boolean = false
    )
}