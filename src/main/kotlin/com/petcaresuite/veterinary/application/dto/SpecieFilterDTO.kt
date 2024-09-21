package com.petcaresuite.veterinary.application.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class SpecieFilterDTO(
    val id: Int?,
    val name: String?,
)