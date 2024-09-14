package com.petcaresuite.veterinary.infrastructure.persistence.mapper

import com.petcaresuite.veterinary.domain.model.Veterinary
import com.petcaresuite.veterinary.infrastructure.persistence.entity.VeterinaryEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface VeterinaryEntityMapper {

    fun toEntity(roleModel: Veterinary): VeterinaryEntity

    fun toDomain(roleEntity: VeterinaryEntity): Veterinary

    fun toDomain(appointments: List<VeterinaryEntity>): List<Veterinary>

}