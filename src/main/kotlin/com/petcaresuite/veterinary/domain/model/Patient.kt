package com.petcaresuite.veterinary.domain.model

data class Patient(
    val patientId: Long?,
    val name: String,
    val species: String?,
    val breed: String?,
    val age: Int?,
    val owner: Owner
)