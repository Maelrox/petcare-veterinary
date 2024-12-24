package com.petcaresuite.veterinary.infrastructure.persistence.repository

import com.petcaresuite.veterinary.infrastructure.persistence.entity.PatientFilesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface JpaPatientFilesRepository : JpaRepository<PatientFilesEntity, Long> {

    fun findAllByPatientPatientIdAndCompanyId(patientId: Long?, companyId: Long): List<PatientFilesEntity>

}
