package com.petcaresuite.veterinary.infrastructure.persistence.mapper

import com.petcaresuite.veterinary.domain.model.Owner
import com.petcaresuite.veterinary.infrastructure.persistence.entity.OwnerEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface OwnerEntityMapper {

    fun toEntity(owner: Owner): OwnerEntity

    fun toDomain(ownerEntity: OwnerEntity): Owner

    fun toDomain(owners: List<OwnerEntity>): List<Owner>

}