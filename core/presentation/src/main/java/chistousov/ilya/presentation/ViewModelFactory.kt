package chistousov.ilya.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

typealias ViewModelCreator<VM> = () -> VM

class ViewModelFactory<VM : ViewModel>(
    private val viewModelCreator: ViewModelCreator<VM>
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return viewModelCreator.invoke() as T
    }
}

inline fun <reified VM : ViewModel> Fragment.viewModelFactory(
    noinline creator: ViewModelCreator<VM>
): Lazy<VM> {
    return viewModels { ViewModelFactory(creator) }
}