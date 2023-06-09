package chistousov.ilya.sign_up.presentation

import androidx.lifecycle.viewModelScope
import chistousov.ilya.common.RequiredFieldException
import chistousov.ilya.presentation.BaseViewModel
import chistousov.ilya.sign_up.R
import chistousov.ilya.sign_up.domain.exceptions.MaxFieldLengthException
import chistousov.ilya.sign_up.domain.exceptions.MinFieldLengthException
import chistousov.ilya.sign_up.domain.exceptions.NotSameFieldsException
import chistousov.ilya.sign_up.domain.usecases.IsSignedUpUseCase
import chistousov.ilya.sign_up.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val isSignedUpUseCase: IsSignedUpUseCase,
    private val router: SignUpRouter
) : BaseViewModel() {

    private val passwordError = MutableStateFlow("")
    private val confirmPasswordError = MutableStateFlow("")
    private val isLoaded = MutableStateFlow(false)

    val signUpState = combine(
        passwordError,
        confirmPasswordError,
        isLoaded,
        ::merge
    ).toFlowValue(State())

    fun checkRegistration() = viewModelScope.launch {
        if (isSignedUpUseCase()) {
            router.launchSignIn()
        }
        isLoaded.value = true
    }

    fun signUp(password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                signUpUseCase(password, confirmPassword)
                router.launchSignIn()
            } catch (e: NotSameFieldsException) {
                confirmPasswordError.value = resource.getString(R.string.not_same_field)
            }
            catch (e: MinFieldLengthException) {
                passwordError.value = resource.getString(R.string.min_count_field)
            }
            catch (e: MaxFieldLengthException) {
                passwordError.value = resource.getString(R.string.max_count_value)
            }
            catch (e: RequiredFieldException) {
                passwordError.value = resource.getString(R.string.required_field)
            }
        }
    }

    private fun merge(
        passwordError: String,
        confirmPasswordError: String,
        isLoaded: Boolean
    ): State {
        return State(passwordError, confirmPasswordError, isLoaded)
    }

    data class State(
        val passwordError: String = "",
        val confirmPasswordError: String = "",
        val isLoaded: Boolean = false
    )
}
