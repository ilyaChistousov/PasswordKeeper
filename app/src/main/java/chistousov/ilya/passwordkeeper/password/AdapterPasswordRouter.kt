package chistousov.ilya.passwordkeeper.password

import androidx.core.os.bundleOf
import chistousov.ilya.navigation.presentation.navigation.NavComponentRouter
import chistousov.ilya.password_details.presentation.PasswordRouter
import chistousov.ilya.password_details.presentation.update.UpdatePasswordFragment
import chistousov.ilya.password_details.presentation.update.UpdatePasswordViewModel
import chistousov.ilya.passwordkeeper.R
import javax.inject.Inject

class AdapterPasswordRouter @Inject constructor(
    private val navComponentRouter: NavComponentRouter
) : PasswordRouter {
    override fun launchCreatePassword() {
        navComponentRouter.launch(R.id.createPasswordFragment)
    }

    override fun launchUpdatePassword(id: Int) {
        navComponentRouter.launch(R.id.updatePasswordFragment, UpdatePasswordFragment.Screen(id))
    }

    override fun goBack() {
        navComponentRouter.pop()
    }
}