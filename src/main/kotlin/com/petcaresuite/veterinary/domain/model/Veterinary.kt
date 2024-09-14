package com.petcaresuite.veterinary.domain.model

data class Veterinary(
    val vetId: Long?,
    val identification: String,
    val identificationType: Int,
    val name: String,
    val phone: String?,
    val specialization: String?
)