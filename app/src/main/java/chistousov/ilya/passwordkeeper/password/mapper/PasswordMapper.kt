package chistousov.ilya.passwordkeeper.password.mapper

import chistousov.ilya.common.Mapper
import chistousov.ilya.data.password.entity.PasswordDataEntity
import chistousov.ilya.password_details.domain.entity.PasswordModel
import javax.inject.Inject
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

interface PasswordMapper : Mapper<PasswordDataEntity, PasswordModel>

class DefaultPasswordMapper @Inject constructor() : PasswordMapper {

    override fun map(input: PasswordDataEntity) = PasswordModel(
        input.id,
        input.title,
        input.password,
        input.login,
        input.email,
        input.url
    )

    override fun reverseMap(output: PasswordModel) = PasswordDataEntity(
        output.id,
        output.title,
        output.password,
        output.login,
        output.email,
        output.url
    )
}