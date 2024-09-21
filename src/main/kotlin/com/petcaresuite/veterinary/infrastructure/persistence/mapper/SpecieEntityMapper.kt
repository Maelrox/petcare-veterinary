package com.petcaresuite.veterinary.infrastructure.persistence.mapper

import com.petcaresuite.veterinary.domain.model.Specie
import com.petcaresuite.veterinary.infrastructure.persistence.entity.SpecieEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface SpecieEntityMapper {

    fun toEntity(specie: Specie): SpecieEntity

    fun toDomain(specieEntity: SpecieEntity): Specie

    fun toDomain(species: List<SpecieEntity>): List<Specie>

}