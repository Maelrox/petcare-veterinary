package com.petcaresuite.veterinary.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "vets")
data class VeterinaryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vet_id")
    val vetId: Long? = null,

    @Column(name = "identification", nullable = false, length = 64)
    val identification: String,

    @Column(name = "identification_type_id", nullable = false)
    val identificationTypeId: Int,

    @Column(name = "name", length = 100)
    val name: String?,

    @Column(name = "phone", length = 15)
    val phone: String?,

    @Column(name = "specialization", length = 100)
    val specialization: String?,

    @Column(name = "company_id", nullable = false)
    val companyId: Long,

)