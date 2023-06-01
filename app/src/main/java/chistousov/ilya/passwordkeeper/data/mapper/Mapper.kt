package chistousov.ilya.passwordkeeper.data.mapper

import chistousov.ilya.passwordkeeper.data.entity.PasswordDbEntity
import chistousov.ilya.passwordkeeper.data.entity.UserDbEntity
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import chistousov.ilya.passwordkeeper.domain.model.UserModel

interface Mapper <T, R> {

    fun map(input: T): R

    fun reverseMap(output: R): T
}

interface ListMapper<T, R> : Mapper<T, R>{
    fun mapList(input: List<T>): List<R>
}

interface PasswordMapper : ListMapper<PasswordDbEntity, PasswordModel>

interface UserMapper : Mapper<UserDbEntity, UserModel>