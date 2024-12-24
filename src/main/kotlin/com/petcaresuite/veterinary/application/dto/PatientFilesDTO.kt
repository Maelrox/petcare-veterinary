package com.petcaresuite.veterinary.application.dto

import java.time.LocalDateTime

data class PatientFilesDTO(
    val fileId: Long? = null,
    val patient: PatientDTO,
    val companyId: Long,
    val description: String,
    val fileDate: LocalDateTime = LocalDateTime.now(),
    val filePath: String
)
