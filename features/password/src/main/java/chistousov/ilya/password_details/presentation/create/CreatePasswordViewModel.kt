package chistousov.ilya.password_details.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.domain.exceptions.EmptyPasswordException
import chistousov.ilya.password_details.domain.exceptions.EmptyTitleException
import chistousov.ilya.password_details.domain.usecases.CreatePasswordUseCase
import chistousov.ilya.password_details.presentation.PasswordRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    private val createPasswordUseCase: CreatePasswordUseCase,
    private val router: PasswordRouter
) : ViewModel() {

    private val _createPasswordState = MutableStateFlow(State())
    val createPasswordState: StateFlow<State> = _createPasswordState.asStateFlow()

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
        } catch (e: EmptyTitleException) { }
        catch (e: EmptyPasswordException) { }
    }

    data class State(
        val titleError: String = "",
        val passwordError: String = "",
    )
}