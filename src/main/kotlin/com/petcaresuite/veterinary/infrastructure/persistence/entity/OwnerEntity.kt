package com.petcaresuite.veterinary.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "owners")
data class OwnerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    val ownerId: Long? = null,

    @Column(name = "identification", nullable = false, length = 64)
    val identification: String,

    @Column(name = "identification_type_id", nullable = false)
    val identificationTypeId: Int,

    @Column(name = "name", length = 100)
    val name: String?,

    @Column(name = "address", length = 128)
    val address: String?,

    @Column(name = "phone", length = 32)
    val phone: String?,

    @Column(name = "company_id", nullable = false)
    val companyId: Long,

)