package chistousov.ilya.passwordkeeper.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.passwordkeeper.domain.usecase.IsSignedUpUseCase
import chistousov.ilya.passwordkeeper.presentation.utils.UiState
import chistousov.ilya.passwordkeeper.presentation.utils.mapToUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val isSignedUpUseCase: IsSignedUpUseCase
) : ViewModel() {

    private val _isRegistered = MutableStateFlow<UiState<Boolean>>(UiState.Loading())
    val isRegistered: StateFlow<UiState<Boolean>> = _isRegistered.asStateFlow()

    fun checkRegistration() {
        viewModelScope.launch {
            _isRegistered.value = isSignedUpUseCase().mapToUiState()
        }
    }
}