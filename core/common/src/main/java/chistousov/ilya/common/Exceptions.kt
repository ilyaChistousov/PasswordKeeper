package chistousov.ilya.common

open class AppException(message: String = "") : Exception(message)

class UserNotFoundException : AppException()

