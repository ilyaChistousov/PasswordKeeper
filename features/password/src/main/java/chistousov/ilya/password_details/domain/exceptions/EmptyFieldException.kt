package chistousov.ilya.password_details.domain.exceptions

import chistousov.ilya.common.AppException
import chistousov.ilya.password_details.domain.entity.PasswordField

class EmptyFieldException(
    val field: PasswordField
) : AppException()