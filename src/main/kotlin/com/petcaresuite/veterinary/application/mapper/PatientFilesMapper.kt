package com.petcaresuite.veterinary.application.mapper

import com.petcaresuite.veterinary.application.dto.PatientFilesDTO
import com.petcaresuite.veterinary.domain.model.PatientFiles
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PatientFilesMapper {

    fun toDTO(patients: List<PatientFiles>): List<PatientFilesDTO>

}