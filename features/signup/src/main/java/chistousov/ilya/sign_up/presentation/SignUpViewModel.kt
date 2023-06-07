package chistousov.ilya.sign_up.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.common.RequiredFieldException
import chistousov.ilya.sign_up.domain.exceptions.MaxFieldLengthException
import chistousov.ilya.sign_up.domain.exceptions.MinFieldLengthException
import chistousov.ilya.sign_up.domain.exceptions.NotSameFieldsException
import chistousov.ilya.sign_up.domain.usecases.IsSignedUpUseCase
import chistousov.ilya.sign_up.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val isSignedUpUseCase: IsSignedUpUseCase,
    private val router: SignUpRouter
) : ViewModel() {

    private val _signUpState = MutableStateFlow(State())
    val signUpState: StateFlow<State> = _signUpState.asStateFlow()

    fun checkRegistration() = viewModelScope.launch {
        if (isSignedUpUseCase()) {
            router.launchSignIn()
        }
        _signUpState.update { it.copy(isLoaded = true) }
    }

    fun signUp(password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                signUpUseCase(password, confirmPassword)
                router.launchSignIn()
            } catch (e: NotSameFieldsException) { }
            catch (e: MinFieldLengthException) {}
            catch (e: MaxFieldLengthException) {}
            catch (e: RequiredFieldException) {}
        }
    }

    data class State(
        val passwordError: String = "",
        val confirmPasswordError: String = "",
        val isLoaded: Boolean = false
    )
}
