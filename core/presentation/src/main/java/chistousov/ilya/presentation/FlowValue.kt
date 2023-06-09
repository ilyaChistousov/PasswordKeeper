package chistousov.ilya.presentation

import androidx.lifecycle.LifecycleOwner

/**
 * Wrapper for value that can be observed with the specified lifecycle
 * User as a replacement of [LiveData]/[StateFlow] in [BaseViewModel]
 * to avoid duplication
 */
interface FlowValue<T> {

    fun observe(lifecycleOwner: LifecycleOwner, listener: (T) -> Unit)

    fun requireValue() : T

    fun getValue(): T?
}