package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.usecase.DeletePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GetListPasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.SearchPasswordUseCase
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
                        passwordList = result.unwrap(),
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
            _passwordListState.update { it.copy(passwordList = result.unwrap(), isLoaded = true) }
        }
    }

    fun deletePassword(id: Int) = viewModelScope.launch {
        deletePasswordUseCase(id)
    }

    data class State(
        val passwordList: List<PasswordModel> = emptyList(),
        val isLoaded: Boolean = false
    )
}