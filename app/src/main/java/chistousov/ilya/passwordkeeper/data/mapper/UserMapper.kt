package chistousov.ilya.passwordkeeper.data.mapper

import chistousov.ilya.passwordkeeper.data.entity.UserDbEntity
import chistousov.ilya.passwordkeeper.domain.model.UserModel
import javax.inject.Inject

class DefaultUserMapper @Inject constructor() : UserMapper {
    override fun map(input: UserDbEntity) = UserModel(
        input.password
    )

    override fun reverseMap(output: UserModel) = UserDbEntity(
        output.password
    )
}