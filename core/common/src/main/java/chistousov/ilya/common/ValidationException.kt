package chistousov.ilya.common

class ValidationException(val validationMap: Map<String, String>) : Exception() {
}