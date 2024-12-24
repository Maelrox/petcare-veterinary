package com.petcaresuite.veterinary.infrastructure.persistence.adapter

import com.petcaresuite.veterinary.application.port.output.PatientPersistencePort
import com.petcaresuite.veterinary.domain.model.Patient
import com.petcaresuite.veterinary.infrastructure.persistence.mapper.PatientEntityMapper
import com.petcaresuite.veterinary.infrastructure.persistence.repository.JpaPatientFilesRepository
import com.petcaresuite.veterinary.infrastructure.persistence.repository.JpaPatientRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PatientRepositoryAdapter(
    private val jpaPatientRepository: JpaPatientRepository,
    private val patientMapper: PatientEntityMapper,
) : PatientPersistencePort {

    override fun findAll(ownerId: Long?, companyId: Long): List<Patient> {
        val owners = jpaPatientRepository.findAllByPatientIdAndCompanyId(ownerId, companyId)
        return patientMapper.toDomain(owners)
    }

    override fun findById(patientId: Long): Patient {
        val patientEntity = jpaPatientRepository.findById(patientId)
            .orElseThrow { EntityNotFoundException("Veterinary with id $patientId not found") }
        return patientMapper.toDomain(patientEntity)
    }

    override fun update(patient: Patient): Patient {
        val patientEntity = patientMapper.toEntity(patient)
        jpaPatientRepository.save(patientEntity)
        return patientMapper.toDomain(patientEntity)
    }

    override fun save(patient: Patient): Patient {
        val patientEntity = patientMapper.toEntity(patient)
        jpaPatientRepository.save(patientEntity)
        return patientMapper.toDomain(patientEntity)
    }

    override fun findAllByFilterPaginated(filter: Patient, pageable: Pageable): Page<Patient> {
        val pagedRolesEntity = jpaPatientRepository.findAllByFilter(filter, pageable)
        return pagedRolesEntity.map { patientMapper.toDomain(it) }
    }

}