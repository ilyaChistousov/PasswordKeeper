package chistousov.ilya.common

interface Logger {

    fun log(message: String)

    fun err(exception: Throwable, message: String? = null)
}