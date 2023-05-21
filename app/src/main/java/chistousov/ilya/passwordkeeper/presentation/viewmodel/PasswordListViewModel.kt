package chistousov.ilya.passwordkeeper.presentation.viewmodel

import androidx.lifecycle.ViewModel
import chistousov.ilya.passwordkeeper.domain.usecase.DeletePasswordUseCase
import chistousov.ilya.passwordkeeper.domain.usecase.GetListPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PasswordListViewModel @Inject constructor(
    private val getListPasswordUseCase: GetListPasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase
) : ViewModel() {


    private val _passwordList = getListPasswordUseCase()

}