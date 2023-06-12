package chistousov.ilya.sign_up.domain.exceptions

import chistousov.ilya.common.AppException
import chistousov.ilya.sign_up.domain.entity.SignUpField

class MinFieldLengthException(
    val field: SignUpField
) : AppException()