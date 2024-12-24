package com.petcaresuite.veterinary.infrastructure.persistence.adapter

import com.petcaresuite.veterinary.application.port.output.PatientFilesPersistencePort
import com.petcaresuite.veterinary.application.port.output.PatientPersistencePort
import com.petcaresuite.veterinary.domain.model.Patient
import com.petcaresuite.veterinary.domain.model.PatientFiles
import com.petcaresuite.veterinary.infrastructure.persistence.mapper.PatientEntityMapper
import com.petcaresuite.veterinary.infrastructure.persistence.mapper.PatientFilesEntityMapper
import com.petcaresuite.veterinary.infrastructure.persistence.repository.JpaPatientFilesRepository
import com.petcaresuite.veterinary.infrastructure.persistence.repository.JpaPatientRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PatientFilesRepositoryAdapter(
    private val jpaPatientFilesRepository: JpaPatientFilesRepository,
    private val patientFilesEntityMapper: PatientFilesEntityMapper
) : PatientFilesPersistencePort {

    override fun findAll(patientFilesId: Long?, companyId: Long): List<PatientFiles> {
        val patientFiles = jpaPatientFilesRepository.findAllByPatientPatientIdAndCompanyId(patientFilesId, companyId)
        return patientFilesEntityMapper.toDomain(patientFiles)
    }

    override fun findById(patientFilesId: Long): PatientFiles {
        val patientEntity = jpaPatientFilesRepository.findById(patientFilesId)
            .orElseThrow { EntityNotFoundException("Veterinary with id $patientFilesId not found") }
        return patientFilesEntityMapper.toDomain(patientEntity)
    }

    override fun update(patientFiles: PatientFiles): PatientFiles {
        val patientEntity = patientFilesEntityMapper.toEntity(patientFiles)
        jpaPatientFilesRepository.save(patientEntity)
        return patientFilesEntityMapper.toDomain(patientEntity)
    }

    override fun deleteById(patientFiles: PatientFiles) {
        jpaPatientFilesRepository.deleteById(patientFiles.fileId!!)
    }

    override fun save(patientFiles: PatientFiles): PatientFiles {
        val patientEntity = patientFilesEntityMapper.toEntity(patientFiles)
        jpaPatientFilesRepository.save(patientEntity)
        return patientFilesEntityMapper.toDomain(patientEntity)
    }

}