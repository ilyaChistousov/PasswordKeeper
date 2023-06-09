package chistousov.ilya.password_details.presentation.passwords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.usecases.DeletePasswordUseCase
import chistousov.ilya.password_details.domain.usecases.GetListPasswordUseCase
import chistousov.ilya.password_details.domain.usecases.SearchPasswordUseCase
import chistousov.ilya.password_details.presentation.PasswordRouter
import chistousov.ilya.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordListViewModel @Inject constructor(
    private val getListPasswordUseCase: GetListPasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val searchPasswordUseCase: SearchPasswordUseCase,
    private val passwordRouter: PasswordRouter
) : BaseViewModel() {

//    private val _passwordListState = MutableStateFlow(State())
//    val passwordListState: StateFlow<State> = _passwordListState.asStateFlow()

    private val passwordList = MutableStateFlow<List<PasswordModel>>(emptyList())
    private val isLoaded = MutableStateFlow(false)

    val passwordListState = combine(
        passwordList,
        isLoaded,
        ::merge
    ).toFlowValue(State())

    init {
        viewModelScope.launch {
            getAllValues()
        }
    }

    fun searchPassword(query: String) = viewModelScope.launch {
//        _passwordListState.update { it.copy(isLoaded = false) }
        isLoaded.value = false
        if (query.isNotEmpty()) {
            searchPasswordUseCase(query).collect { result ->
                passwordList.value = result.unwrap()
                isLoaded.value = true
            }
        } else {
            getAllValues()
        }
    }

    private suspend fun getAllValues() {
        getListPasswordUseCase().collect { result ->
            passwordList.value = result.unwrap()
            isLoaded.value = true
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

    private fun merge(
        passwordModelList: List<PasswordModel>,
        isLoaded: Boolean
    ): State {
        return State(passwordModelList, isLoaded)
    }

    data class State(
        val passwordModelList: List<PasswordModel> = emptyList(),
        val isLoaded: Boolean = false
    )
}