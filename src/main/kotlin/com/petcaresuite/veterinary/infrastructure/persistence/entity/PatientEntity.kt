package com.petcaresuite.veterinary.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "patients")
data class PatientEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    val patientId: Long? = null,

    @Column(name = "name", length = 100)
    val name: String?,

    @Column(name = "breed", length = 24)
    val breed: String?,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specie_id", nullable = false)
    val specie: SpecieEntity?,

    @Column(name = "age")
    val age: Int?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    val owner: OwnerEntity,

)