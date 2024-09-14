package com.petcaresuite.veterinary.application.service

import com.petcaresuite.veterinary.application.dto.VeterinaryDTO
import com.petcaresuite.veterinary.application.mapper.VeterinaryMapper
import com.petcaresuite.veterinary.application.port.input.VeterinaryUseCase
import com.petcaresuite.veterinary.application.port.output.VeterinaryPersistencePort
import org.springframework.stereotype.Service

@Service
class VeterinaryService(
    private val veterinaryPersistencePort: VeterinaryPersistencePort,
    private val veterinaryMapper: VeterinaryMapper,
) :
    VeterinaryUseCase {

    override fun getAll(veterinaryId: Long?, companyId: Long): List<VeterinaryDTO> {
        val veterinaries = veterinaryPersistencePort.findAll(veterinaryId, companyId)
        return veterinaryMapper.toDTO(veterinaries)
    }

}