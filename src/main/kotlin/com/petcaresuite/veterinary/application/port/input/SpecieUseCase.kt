package com.petcaresuite.veterinary.application.port.input

import com.petcaresuite.veterinary.application.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SpecieUseCase {
    
    fun getAll(): List<SpecieDTO>

    fun save(patientDTO: SpecieDTO): ResponseDTO

    fun update(patientDTO: SpecieDTO): ResponseDTO?

    fun getAllByFilterPaginated(filterDTO: SpecieFilterDTO, pageable: Pageable):   Page<SpecieDTO>

}