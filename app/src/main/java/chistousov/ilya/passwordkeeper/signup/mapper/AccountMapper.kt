package chistousov.ilya.passwordkeeper.signup.mapper

import chistousov.ilya.common.Mapper
import chistousov.ilya.data.account.entity.AccountDataEntity
import chistousov.ilya.sign_up.domain.entity.AccountModel
import javax.inject.Inject

interface AccountMapper : Mapper<AccountDataEntity, AccountModel>

class DefaultAccountMapper @Inject constructor() : AccountMapper {

    override fun map(input: AccountDataEntity) = AccountModel(
        input.password
    )

    override fun reverseMap(output: AccountModel) =  AccountDataEntity(
        output.password
    )
}