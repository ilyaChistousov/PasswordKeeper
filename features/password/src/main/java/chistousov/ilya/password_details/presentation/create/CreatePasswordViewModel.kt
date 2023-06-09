package chistousov.ilya.password_details.presentation.create

import androidx.lifecycle.viewModelScope
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.domain.exceptions.EmptyPasswordException
import chistousov.ilya.password_details.domain.exceptions.EmptyTitleException
import chistousov.ilya.password_details.domain.usecases.CreatePasswordUseCase
import chistousov.ilya.password_details.presentation.PasswordRouter
import chistousov.ilya.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    private val createPasswordUseCase: CreatePasswordUseCase,
    private val router: PasswordRouter
) : BaseViewModel() {

    private val titleError = MutableStateFlow("")
    private val passwordError = MutableStateFlow("")

    val createPasswordState = combine(
        titleError,
        passwordError,
        ::merge
    ).toFlowValue(State())

    fun createPassword(
        title: String,
        password: String,
        login: String,
        email: String,
        url: String
    ) = viewModelScope.launch{
        try {
            createPasswordUseCase(title, password, login, email, url)
            router.goBack()
        } catch (e: EmptyTitleException) {
            titleError.value = resource.getString(R.string.empty_field)
        }
        catch (e: EmptyPasswordException) {
            passwordError.value = resource.getString(R.string.empty_field)
        }
    }

    fun merge(
        titleError: String,
        passwordError: String
    ): State {
        return State(titleError, passwordError)
    }

    data class State(
        val titleError: String = "",
        val passwordError: String = "",
    )
}