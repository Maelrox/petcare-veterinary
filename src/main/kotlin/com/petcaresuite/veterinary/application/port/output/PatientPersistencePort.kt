package com.petcaresuite.veterinary.application.port.output

import com.petcaresuite.veterinary.domain.model.Patient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PatientPersistencePort {

     fun findAll(ownerId: Long?, companyId: Long): List<Patient>

     fun save(patient: Patient): Patient

     fun findAllByFilterPaginated(filter: Patient, pageable: Pageable): Page<Patient>

     fun findById(patientId: Long): Patient

     fun update(patient: Patient): Patient

}