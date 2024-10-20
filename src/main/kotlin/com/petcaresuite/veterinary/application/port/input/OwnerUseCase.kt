package com.petcaresuite.veterinary.application.port.input

import com.petcaresuite.veterinary.application.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OwnerUseCase {
    
    fun getAll(ownerId: Long?, identification: String?, name: String?, companyId: Long): List<OwnerDTO>

    fun save(ownerDTO: OwnerDTO): ResponseDTO

    fun update(veterinaryDTO: OwnerDTO): ResponseDTO?

    fun getAllByFilterPaginated(filterDTO: OwnerFilterDTO, pageable: Pageable): Page<OwnerDTO>

}