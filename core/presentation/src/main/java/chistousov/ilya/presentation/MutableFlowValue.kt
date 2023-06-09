package chistousov.ilya.presentation

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow

class MutableFlowValue<T>(
    private val flow: MutableStateFlow<T>
) : FlowValue<T> {

    override fun observe(lifecycleOwner: LifecycleOwner, listener: (T) -> Unit) {
        this.flow.launchWhenStarted(lifecycleOwner, listener)
    }

    override fun requireValue(): T {
        return this.flow.value ?: throw IllegalStateException("Value is not assigned")
    }

    override fun getValue(): T? {
        return this.flow.value
    }

    fun setValue(value: T) {
        this.flow.value = value
    }
}