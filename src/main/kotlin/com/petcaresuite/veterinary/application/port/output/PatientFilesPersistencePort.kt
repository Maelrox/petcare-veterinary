package com.petcaresuite.veterinary.application.port.output

import com.petcaresuite.veterinary.domain.model.PatientFiles

interface PatientFilesPersistencePort {

     fun findAll(patientFilesId: Long?, companyId: Long): List<PatientFiles>

     fun findById(patientFilesId: Long): PatientFiles

     fun save(patientFiles: PatientFiles): PatientFiles

     fun update(patientFiles: PatientFiles): PatientFiles

     fun deleteById(patientFiles: PatientFiles)

}