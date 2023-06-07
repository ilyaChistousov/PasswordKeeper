package chistousov.ilya.passwordkeeper.signin

import chistousov.ilya.navigation.presentation.navigation.NavComponentRouter
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.sign_in.presentation.SignInRouter
import javax.inject.Inject

class AdapterSignInRouter @Inject constructor(
    private val navComponentRouter: NavComponentRouter
) : SignInRouter {

    override fun launchPasswordList() {
        navComponentRouter.launch(R.id.passwordListFragment)
    }
}