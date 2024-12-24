package com.petcaresuite.veterinary.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "patient_files")
data class PatientFilesEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    val fileId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false)
    val patient: PatientEntity,

    @Column(name = "company_id", nullable = false)
    val companyId: Long,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "file_date", nullable = false)
    val fileDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "file_path", nullable = false)
    val filePath: String
)
