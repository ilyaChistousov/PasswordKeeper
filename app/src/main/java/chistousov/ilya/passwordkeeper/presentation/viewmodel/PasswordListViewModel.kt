package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.usecase.DeletePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GetListPasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.SearchPasswordUseCase
import chistousov.ilya.passwordkeeper.utils.UiState
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
        if (query.isNotEmpty()) {
            searchPasswordUseCase(query).collect {
                // баг при компиляции, поэтому приходится вручную кастить
                _passwordList.value = it as UiState<List<PasswordModel>>
            }
        } else {
            getAllValues()
        }
    }

    private suspend fun getAllValues() {
        getListPasswordUseCase().collect {
            // баг при компиляции, поэтому приходится вручную кастить
            _passwordList.value = it as UiState<List<PasswordModel>>
        }
    }

    fun deletePassword(id: Int) = viewModelScope.launch {
        deletePasswordUseCase(id)
    }
}