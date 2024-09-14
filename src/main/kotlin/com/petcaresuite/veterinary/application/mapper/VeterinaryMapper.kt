package com.petcaresuite.veterinary.application.mapper

import com.petcaresuite.veterinary.application.dto.VeterinaryDTO
import com.petcaresuite.veterinary.application.dto.VeterinaryFilterDTO
import com.petcaresuite.veterinary.domain.model.Veterinary
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface VeterinaryMapper {

    fun toDomain(veterinaryDTO: VeterinaryDTO): Veterinary

    fun toDTO(veterinary: Veterinary): VeterinaryDTO

    fun toDTO(veterinaries: List<Veterinary>): List<VeterinaryDTO>

    fun toDomain(veterinaryDTO: VeterinaryFilterDTO): Veterinary

}