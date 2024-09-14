package com.petcaresuite.veterinary.application.mapper

import com.petcaresuite.veterinary.application.dto.PatientDTO
import com.petcaresuite.veterinary.application.dto.PatientFilterDTO
import com.petcaresuite.veterinary.domain.model.Patient
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
interface PatientMapper {

    fun toDomain(ownerDTO: PatientDTO): Patient

    fun toDTO(patient: Patient): PatientDTO

    fun toDTO(patients: List<Patient>): List<PatientDTO>

    fun toDomain(patientDTO: PatientFilterDTO): Patient

}