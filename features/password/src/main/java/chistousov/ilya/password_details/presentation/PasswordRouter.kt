package chistousov.ilya.password_details.presentation

interface PasswordRouter {

    fun launchCreatePassword()
    fun launchUpdatePassword(id: Int)
    fun goBack()
}