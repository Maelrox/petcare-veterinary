package com.petcaresuite.veterinary.domain.model

import java.time.LocalDateTime

data class PatientFiles(
    val fileId: Long? = null,
    val patient: Patient,
    val companyId: Long,
    val description: String,
    val fileDate: LocalDateTime = LocalDateTime.now(),
    val filePath: String
)
