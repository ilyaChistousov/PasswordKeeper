package chistousov.ilya.password_details.presentation.update

import androidx.lifecycle.viewModelScope
import chistousov.ilya.password_details.R
import chistousov.ilya.password_details.domain.entity.PasswordField
import chistousov.ilya.password_details.domain.entity.PasswordModel
import chistousov.ilya.password_details.domain.exceptions.EmptyFieldException
import chistousov.ilya.password_details.domain.usecases.DeletePasswordUseCase
import chistousov.ilya.password_details.domain.usecases.GetPasswordUseCase
import chistousov.ilya.password_details.domain.usecases.UpdatePasswordUseCase
import chistousov.ilya.password_details.presentation.PasswordRouter
import chistousov.ilya.presentation.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class UpdatePasswordViewModel @AssistedInject constructor(
    @Assisted private val screen: UpdatePasswordFragment.Screen,
    private val getPasswordUseCase: GetPasswordUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase,
    private val router: PasswordRouter
) : BaseViewModel() {

    private val selectedPasswordModel = MutableStateFlow<PasswordModel?>(null)
    private val isLoaded = MutableStateFlow(false)

    val focusFieldEventFlow = flowEvent<PasswordField>()
    val fieldErrorMessageEventFlow = flowEvent<Pair<PasswordField, String>>()

    val updatePasswordState = combine(
        selectedPasswordModel,
        isLoaded,
        ::State
    ).toFlowValue(State())

    init {
        getPassword()
    }

    private fun getPassword() = viewModelScope.launch {
        selectedPasswordModel.value = getPasswordUseCase(screen.id).unwrap()
        isLoaded.value = true
    }

    fun updatePassword(
        title: String,
        password: String,
        login: String,
        email: String,
        url: String
    ) {
        viewModelScope.launch {
            try {
                updatePasswordUseCase(screen.id, title, password, login, email, url)
                router.goBack()
            } catch (e: EmptyFieldException) {
                handleEmptyFieldException(e)
            }
        }
    }

    private fun handleEmptyFieldException(e: EmptyFieldException) {
        focusField(e.field)
        setFieldError(e.field, resource.getString(R.string.empty_field))
    }

    private fun focusField(field: PasswordField) {
        focusFieldEventFlow.publish(field)
    }

    private fun setFieldError(field: PasswordField, errorMessage: String) {
        fieldErrorMessageEventFlow.publish(field to errorMessage)
    }

    fun deletePassword() {
        showDeleteDialog {
            viewModelScope.launch {
                deletePasswordUseCase(screen.id)
                router.goBack()
            }
        }
    }

    data class State(
        val selectedPasswordModel: PasswordModel? = null,
        val isLoaded: Boolean = false
    )

    @AssistedFactory
    interface Factory {
        fun create(screen: UpdatePasswordFragment.Screen): UpdatePasswordViewModel
    }
}