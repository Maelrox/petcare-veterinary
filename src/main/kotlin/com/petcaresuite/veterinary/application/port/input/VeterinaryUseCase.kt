package com.petcaresuite.veterinary.application.port.input

import com.petcaresuite.veterinary.application.dto.ResponseDTO
import com.petcaresuite.veterinary.application.dto.VeterinaryDTO


interface VeterinaryUseCase {
    
    fun getAll(veterinaryId: Long?, companyId: Long): List<VeterinaryDTO>

    fun save(veterinaryDTO: VeterinaryDTO): ResponseDTO

}