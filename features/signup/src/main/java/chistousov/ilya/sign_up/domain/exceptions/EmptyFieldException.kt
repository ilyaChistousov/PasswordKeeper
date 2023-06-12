package chistousov.ilya.sign_up.domain.exceptions

import chistousov.ilya.common.AppException
import chistousov.ilya.sign_up.domain.entity.SignUpField

class EmptyFieldException(
    val field: SignUpField
) : AppException() {
}