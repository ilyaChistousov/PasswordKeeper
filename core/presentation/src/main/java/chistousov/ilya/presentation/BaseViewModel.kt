package chistousov.ilya.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chistousov.ilya.common.AlertDialogConfig
import chistousov.ilya.common.Core
import chistousov.ilya.common.Logger
import chistousov.ilya.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected val resource: Resource get() = Core.resource

    protected val logger: Logger get() = Core.logger

    protected fun showToast(message: String) {
        Core.commonUi.showToast(message)
    }

    protected var <T>FlowValue<T>.value: T
        get() = this.requireValue()
        set(value) {
            (this as MutableFlowValue).setValue(value)
        }

    protected fun <T> flowValue(value: T): FlowValue<T> {
        return MutableFlowValue(MutableStateFlow(value))
    }

    protected fun <T> flowEvent(): FlowEventValue<T> {
        return MutableFlowValue(MutableStateFlow(Event(null)))
    }

    protected fun <T> FlowEventValue<T>.publish(event: T) {
        this.value = Event(event)
    }

    protected fun <T> Flow<T>.toFlowValue(initialState: T): FlowValue<T> {
        val flowState = flowValue(initialState)

        viewModelScope.launch {
            collect {
                flowState.value = it
            }
        }

        return flowState
    }

    protected fun showDeleteDialog(positiveButtonCallback: () -> Unit) {
        Core.commonUi.showAlertDialog(AlertDialogConfig(
            title = resource.getString(R.string.dialog_delete_title),
            message = resource.getString(R.string.dialog_delete_message),
            positiveButtonText = resource.getString(R.string.dialog_delete_positive_button),
            negativeButtonText = resource.getString(R.string.dialog_delete_negative_button),
            positiveButtonCallback = positiveButtonCallback
        ))
    }
}