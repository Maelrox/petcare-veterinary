package com.petcaresuite.veterinary.application.mapper

import com.petcaresuite.veterinary.application.dto.SpecieDTO
import com.petcaresuite.veterinary.application.dto.SpecieFilterDTO
import com.petcaresuite.veterinary.domain.model.Specie
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface SpecieMapper {

    fun toDomain(ownerDTO: SpecieDTO): Specie

    fun toDTO(specie: Specie): SpecieDTO

    fun toDTO(species: List<Specie>): List<SpecieDTO>

    fun toDomain(specieDTO: SpecieFilterDTO): Specie

}