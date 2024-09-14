package com.petcaresuite.veterinary.domain.model

data class Veterinary(
    val vetId: Long?,
    val identification: String,
    val identificationTypeId: Int,
    val name: String,
    val phone: String?,
    val specialization: String?,
    val companyId: Long?,
)