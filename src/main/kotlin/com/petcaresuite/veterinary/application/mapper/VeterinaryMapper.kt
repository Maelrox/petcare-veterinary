package com.petcaresuite.veterinary.application.mapper

import com.petcaresuite.appointment.application.dto.VeterinaryDTO
import com.petcaresuite.veterinary.domain.model.Veterinary
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface VeterinaryMapper {

    fun toDomain(veterinaryDTO: VeterinaryDTO): Veterinary

    fun toDTO(appointment: Veterinary): VeterinaryDTO

    fun toDTO(appointments: List<Veterinary>): List<VeterinaryDTO>

}