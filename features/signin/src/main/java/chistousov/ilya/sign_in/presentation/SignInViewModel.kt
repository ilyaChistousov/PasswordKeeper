package chistousov.ilya.sign_in.presentation

import androidx.lifecycle.viewModelScope
import chistousov.ilya.common.UserNotFoundException
import chistousov.ilya.presentation.BaseViewModel
import chistousov.ilya.sign_in.R
import chistousov.ilya.sign_in.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val router: SignInRouter
) : BaseViewModel() {

    val errorMessage = flowValue(State())

    fun signIn(password: String) = viewModelScope.launch {
        try {
            signInUseCase(password)
            router.launchPasswordList()
        } catch (e: UserNotFoundException) {
            errorMessage.value = State(resource.getString(R.string.wrong_password))
        }
    }

    data class State(
        val errorMessage: String = ""
    )
}