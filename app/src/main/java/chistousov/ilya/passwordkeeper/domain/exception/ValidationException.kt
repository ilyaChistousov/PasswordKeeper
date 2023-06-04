package chistousov.ilya.passwordkeeper.domain.exception

class ValidationException(val validationMap: Map<String, Int?>) : Exception() {
}