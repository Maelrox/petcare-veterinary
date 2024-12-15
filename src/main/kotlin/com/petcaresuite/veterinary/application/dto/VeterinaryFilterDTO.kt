package com.petcaresuite.veterinary.application.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class VeterinaryFilterDTO(
    val vetId: Long?,
    val identification: String?,
    val identificationType: IdentificationTypeDTO?,
    val name: String?,
    val phone: String?,
    val specialization: String?,
    var companyId: Long?
)