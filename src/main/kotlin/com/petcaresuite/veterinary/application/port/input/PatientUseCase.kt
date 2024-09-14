package com.petcaresuite.veterinary.application.port.input

import com.petcaresuite.veterinary.application.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PatientUseCase {
    
    fun getAll(patientId: Long?, companyId: Long): List<PatientDTO>

    fun save(patientDTO: PatientDTO): ResponseDTO

    fun update(patientDTO: PatientDTO): ResponseDTO?

    fun getAllByFilterPaginated(filterDTO: PatientFilterDTO, pageable: Pageable):   Page<PatientDTO>


}