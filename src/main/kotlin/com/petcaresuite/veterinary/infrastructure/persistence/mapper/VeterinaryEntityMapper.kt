package com.petcaresuite.veterinary.infrastructure.persistence.mapper

import com.petcaresuite.veterinary.domain.model.Veterinary
import com.petcaresuite.veterinary.infrastructure.persistence.entity.VeterinaryEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface VeterinaryEntityMapper {

    fun toEntity(veterinary: Veterinary): VeterinaryEntity

    fun toDomain(veterinaryEntity: VeterinaryEntity): Veterinary

    fun toDomain(veterinaries: List<VeterinaryEntity>): List<Veterinary>

}