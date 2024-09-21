package com.petcaresuite.veterinary.domain.model

data class Patient(
    val patientId: Long?,
    val name: String?,
    val specie: Specie?,
    val breed: String?,
    val age: Int?,
    val owner: Owner?,
    val ownerId: Long?,
    val companyId: Long?
)