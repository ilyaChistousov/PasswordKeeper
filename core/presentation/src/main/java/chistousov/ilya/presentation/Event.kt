package chistousov.ilya.presentation

import androidx.lifecycle.LifecycleOwner

class Event<T>(
    value: T?
) {

    private var _value: T? = value

    fun get(): T? = _value.also { _value = null }
}

typealias FlowEventValue<T> = FlowValue<Event<T>>

fun <T> FlowValue<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, action: (T) -> Unit) {
    observe(lifecycleOwner) { event ->
        val value = event.get() ?: return@observe
        action(value)
    }
}