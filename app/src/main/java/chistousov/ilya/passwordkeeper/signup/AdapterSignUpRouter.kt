package chistousov.ilya.passwordkeeper.signup

import chistousov.ilya.navigation.presentation.navigation.NavComponentRouter
import chistousov.ilya.passwordkeeper.R
import chistousov.ilya.sign_up.presentation.SignUpRouter
import javax.inject.Inject

class AdapterSignUpRouter @Inject constructor(
    private val navComponentRouter: NavComponentRouter
) : SignUpRouter {

    override fun launchSignIn() {
        navComponentRouter.launch(R.id.signInFragment)
    }
}