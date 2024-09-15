package com.petcaresuite.veterinary.application.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.petcaresuite.veterinary.domain.model.Owner

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PatientDTO(
    val patientId: Long?,
    val name: String,
    val species: String,
    val breed: String?,
    val age: String?,
    var companyId: Long?,
    var owner: Owner
)