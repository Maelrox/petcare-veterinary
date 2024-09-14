package com.petcaresuite.veterinary.application.mapper

import com.petcaresuite.veterinary.application.dto.OwnerDTO
import com.petcaresuite.veterinary.application.dto.OwnerFilterDTO
import com.petcaresuite.veterinary.domain.model.Owner
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface OwnerMapper {

    fun toDomain(ownerDTO: OwnerDTO): Owner

    fun toDTO(owner: Owner): OwnerDTO

    fun toDTO(owners: List<Owner>): List<OwnerDTO>

    fun toDomain(ownerDTO: OwnerFilterDTO): Owner

}