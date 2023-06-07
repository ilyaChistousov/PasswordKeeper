package chistousov.ilya.password_details.presentation.passwords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.usecases.DeletePasswordUseCase
import chistousov.ilya.password_details.domain.usecases.GetListPasswordUseCase
import chistousov.ilya.password_details.domain.usecases.SearchPasswordUseCase
import chistousov.ilya.password_details.presentation.PasswordRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordListViewModel @Inject constructor(
    private val getListPasswordUseCase: GetListPasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val searchPasswordUseCase: SearchPasswordUseCase,
    private val passwordRouter: PasswordRouter
) : ViewModel() {

    private val _passwordListState = MutableStateFlow(State())
    val passwordListState: StateFlow<State> = _passwordListState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllValues()
        }
    }

    fun searchPassword(query: String) = viewModelScope.launch {
        _passwordListState.update { it.copy(isLoaded = false) }
        if (query.isNotEmpty()) {
            searchPasswordUseCase(query).collect { result ->
                _passwordListState.update {
                    it.copy(
                        passwordModelList = result.unwrap(),
                        isLoaded = true
                    )
                }
            }
        } else {
            getAllValues()
        }
    }

    private suspend fun getAllValues() {
        getListPasswordUseCase().collect { result ->
            _passwordListState.update { it.copy(passwordModelList = result.unwrap(), isLoaded = true) }
        }
    }

    fun deletePassword(id: Int) = viewModelScope.launch {
        deletePasswordUseCase(id)
    }

    fun launchUpdatePassword(id: Int) {
        passwordRouter.launchUpdatePassword(id)
    }

    fun launchCreatePassword() {
        passwordRouter.launchCreatePassword()
    }

    data class State(
        val passwordModelList: List<PasswordModel> = emptyList(),
        val isLoaded: Boolean = false
    )
}