package chistousov.ilya.common

open class AppException(message: String = "") : Exception(message)

class RequiredFieldException : AppException()

class UserNotFoundException : AppException()

