package chistousov.ilya.password_details.presentation.create

import androidx.lifecycle.viewModelScope
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.domain.entity.PasswordField
import chistousov.ilya.password_details.domain.exceptions.EmptyFieldException
import chistousov.ilya.password_details.domain.usecases.CreatePasswordUseCase
import chistousov.ilya.password_details.presentation.PasswordRouter
import chistousov.ilya.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(
    private val createPasswordUseCase: CreatePasswordUseCase,
    private val router: PasswordRouter
) : BaseViewModel() {

    val focusFieldEventFlow = flowEvent<PasswordField>()

    val createPasswordState = flowValue(State())

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
        } catch (e: EmptyFieldException) {
            handleEmptyFieldException(e)
        }
    }

    fun clearField() {
        createPasswordState.value = State()
    }

    private fun handleEmptyFieldException(e: EmptyFieldException) {
        focusField(e.field)
        setFieldError(e.field, resource.getString(R.string.empty_field))
    }

    private fun focusField(field: PasswordField) {
        focusFieldEventFlow.publish(field)
    }

    private fun setFieldError(field: PasswordField, errorMessage: String) {
        createPasswordState.value = State(field to errorMessage)
    }

    data class State(
        val fieldError: Pair<PasswordField, String>? = null
    )
}