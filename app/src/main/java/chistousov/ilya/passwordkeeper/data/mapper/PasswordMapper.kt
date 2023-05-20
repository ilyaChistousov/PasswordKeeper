package chistousov.ilya.passwordkeeper.data.mapper

import chistousov.ilya.passwordkeeper.data.entity.PasswordDbEntity
import chistousov.ilya.passwordkeeper.domain.model.PasswordModel
import javax.inject.Inject

class PasswordMapper @Inject constructor(){

    fun mapFromDbEntityToModel(entity: PasswordDbEntity) = PasswordModel(
        entity.id,
        entity.title,
        entity.password,
        entity.login,
        entity.email,
        entity.url,
    )

    fun mapFromModelToDbEntity(model: PasswordModel) = PasswordDbEntity(
        model.id,
        model.title,
        model.password,
        model.login,
        model.email,
        model.url
    )

    fun mapFromDbEntityListToModelList(entities: List<PasswordDbEntity>) =
        entities.map { mapFromDbEntityToModel(it) }
}