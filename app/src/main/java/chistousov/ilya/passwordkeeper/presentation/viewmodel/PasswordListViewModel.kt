package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.usecase.DeletePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GetListPasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.SearchPasswordUseCase
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordListViewModel @Inject constructor(
    private val getListPasswordUseCase: GetListPasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val searchPasswordUseCase: SearchPasswordUseCase,
) : ViewModel() {

    private val _passwordList = MutableStateFlow<UiState<List<PasswordModel>>>(UiState.Loading())
    val passwordList : StateFlow<UiState<List<PasswordModel>>> = _passwordList
    init {
        viewModelScope.launch {
            getAllValues()
        }
    }

    fun searchPassword(query: String) = viewModelScope.launch {
        _passwordList.value = UiState.Loading()
        if (query.isNotEmpty()) {
            searchPasswordUseCase(query).collect {
                _passwordList.value = it.mapToUiState()
            }
        } else {
            getAllValues()
        }
    }

    private suspend fun getAllValues() {
        getListPasswordUseCase().collect {
            _passwordList.value = it.mapToUiState()
        }
    }

    fun deletePassword(id: Int) = viewModelScope.launch {
        deletePasswordUseCase(id)
    }
}