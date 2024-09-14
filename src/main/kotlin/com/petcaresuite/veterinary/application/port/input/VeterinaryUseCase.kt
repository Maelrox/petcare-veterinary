package com.petcaresuite.veterinary.application.port.input

import com.petcaresuite.veterinary.application.dto.ResponseDTO
import com.petcaresuite.veterinary.application.dto.VeterinaryDTO
import com.petcaresuite.veterinary.application.dto.VeterinaryFilterDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface VeterinaryUseCase {
    
    fun getAll(veterinaryId: Long?, companyId: Long): List<VeterinaryDTO>

    fun save(veterinaryDTO: VeterinaryDTO): ResponseDTO

    fun update(veterinaryDTO: VeterinaryDTO): ResponseDTO?

    fun getAllByFilterPaginated(filterDTO: VeterinaryFilterDTO, pageable: Pageable):   Page<VeterinaryDTO>


}