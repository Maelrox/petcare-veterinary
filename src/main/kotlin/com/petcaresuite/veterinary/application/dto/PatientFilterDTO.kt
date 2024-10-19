package com.petcaresuite.veterinary.application.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PatientFilterDTO(
    val patientId: Long?,
    val name: String?,
    val species: SpecieFilterDTO?,
    val breed: String?,
    val age: String?,
    var companyId: Long?,
    var owner: OwnerFilterDTO?,
    var ownerId: Long?,
    var ownerName: String?,

    )