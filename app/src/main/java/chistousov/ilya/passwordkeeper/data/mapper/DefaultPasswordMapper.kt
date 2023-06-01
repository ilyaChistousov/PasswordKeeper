package chistousov.ilya.passwordkeeper.data.mapper

import chistousov.ilya.passwordkeeper.data.entity.PasswordDbEntity
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import javax.inject.Inject

class DefaultPasswordMapper @Inject constructor() : PasswordMapper {

    override fun map(input: PasswordDbEntity) = PasswordModel(
        input.id,
        input.title,
        input.password,
        input.login,
        input.email,
        input.url
    )

    override fun reverseMap(output: PasswordModel) = PasswordDbEntity(
        output.id,
        output.title,
        output.password,
        output.login,
        output.email,
        output.url
    )

    override fun mapList(input: List<PasswordDbEntity>): List<PasswordModel> {
        return input.map { map(it) }
    }
}