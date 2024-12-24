package com.petcaresuite.veterinary.infrastructure.persistence.mapper

import com.petcaresuite.veterinary.domain.model.PatientFiles
import com.petcaresuite.veterinary.infrastructure.persistence.entity.PatientFilesEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PatientFilesEntityMapper {

    fun toEntity(patientFile: PatientFiles): PatientFilesEntity

    fun toDomain(patientFile: PatientFilesEntity): PatientFiles

    fun toDomain(patientFiles: List<PatientFilesEntity>): List<PatientFiles>

}