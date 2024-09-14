package com.petcaresuite.veterinary.infrastructure.persistence.mapper

import com.petcaresuite.veterinary.domain.model.Patient
import com.petcaresuite.veterinary.infrastructure.persistence.entity.PatientEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PatientEntityMapper {

    fun toEntity(patient: Patient): PatientEntity

    fun toDomain(patientEntity: PatientEntity): Patient

    fun toDomain(patients: List<PatientEntity>): List<Patient>

}