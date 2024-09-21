package com.petcaresuite.veterinary.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "species")
data class SpecieEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Int? = null,

    @Column(name = "name", length = 48)
    val name: String?,

)