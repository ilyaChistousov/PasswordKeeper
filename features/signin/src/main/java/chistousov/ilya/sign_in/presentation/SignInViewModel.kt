package chistousov.ilya.sign_in.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.sign_in.domain.exceptions.UserNotFoundException
import chistousov.ilya.sign_in.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val router: SignInRouter
) : ViewModel() {

    private val _errorMessage = MutableStateFlow(State())
    val errorMessage: StateFlow<State> = _errorMessage.asStateFlow()

    fun signIn(password: String) = viewModelScope.launch {
        try {
            signInUseCase(password)
            router.launchPasswordList()
        } catch (e: UserNotFoundException) {

        }
    }

    data class State(
        val errorMessage: String = ""
    )
}