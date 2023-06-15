package chistousov.ilya.sign_up.presentation

import androidx.lifecycle.viewModelScope
import chistousov.ilya.presentation.BaseViewModel
import chistousov.ilya.sign_up.R
import chistousov.ilya.sign_up.domain.entity.SignUpField
import chistousov.ilya.sign_up.domain.exceptions.EmptyFieldException
import chistousov.ilya.sign_up.domain.exceptions.MaxFieldLengthException
import chistousov.ilya.sign_up.domain.exceptions.MinFieldLengthException
import chistousov.ilya.sign_up.domain.exceptions.NotSameFieldsException
import chistousov.ilya.sign_up.domain.usecases.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val router: SignUpRouter
) : BaseViewModel() {

    val focusFieldFlowEvent = flowEvent<SignUpField>()
    val clearFieldFlowEvent = flowEvent<SignUpField>()

    val signUpState = flowValue(State())

    fun signUp(password: String, confirmPassword: String) {
        viewModelScope.launch {
            try {
                signUpUseCase(password, confirmPassword)
                router.launchSignIn()
            } catch (e: EmptyFieldException) {
                handleEmptyFieldException(e)
            } catch (e: MaxFieldLengthException) {
                handleMaxFieldLengthException(e)
            } catch (e: MinFieldLengthException) {
                handleMinFieldLengthException(e)
            } catch (e: NotSameFieldsException) {
                handleNotSameFieldsException()
            }
        }
    }

    private fun handleEmptyFieldException(emptyFieldException: EmptyFieldException) {
        focusField(emptyFieldException.field)
        setFieldError(emptyFieldException.field, resource.getString(R.string.empty_field))
    }

    private fun handleMaxFieldLengthException(maxFieldLengthException: MaxFieldLengthException) {
        focusField(maxFieldLengthException.field)
        setFieldError(maxFieldLengthException.field, resource.getString(R.string.max_length_value))
    }

    private fun handleMinFieldLengthException(minFieldLengthException: MinFieldLengthException) {
        focusField(minFieldLengthException.field)
        setFieldError(minFieldLengthException.field, resource.getString(R.string.min_length_field))
    }

    private fun handleNotSameFieldsException() {
        focusField(SignUpField.CONFIRM_PASSWORD)
        clearField(SignUpField.CONFIRM_PASSWORD)
        setFieldError(SignUpField.CONFIRM_PASSWORD, resource.getString(R.string.not_same_field))
    }

    private fun focusField(field: SignUpField) {
        focusFieldFlowEvent.publish(field)
    }

    private fun clearField(field: SignUpField) {
        clearFieldFlowEvent.publish(field)
    }

    private fun setFieldError(field: SignUpField, errorMessage: String) {
        signUpState.value = State(field to errorMessage)
    }

    data class State(
        val fieldErrorMessage: Pair<SignUpField, String>? = null
    )
}
