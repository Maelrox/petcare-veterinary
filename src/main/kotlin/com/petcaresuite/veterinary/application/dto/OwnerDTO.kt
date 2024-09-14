package com.petcaresuite.veterinary.application.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OwnerDTO(
    val ownerId: Long?,
    val identification: String,
    val identificationTypeId: Int,
    val name: String?,
    val address: String?,
    val phone: String?,
    var companyId: Long?
)