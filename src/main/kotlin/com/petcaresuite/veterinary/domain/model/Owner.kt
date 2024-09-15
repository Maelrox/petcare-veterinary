package com.petcaresuite.veterinary.domain.model

data class Owner(
    val ownerId: Long?,
    val identification: String?,
    val name: String?,
    val address: String?,
    val phone: String?,
    val identificationTypeId: Int?,
    val companyId: Long?,
)