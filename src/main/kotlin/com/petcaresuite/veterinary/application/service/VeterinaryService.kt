package com.petcaresuite.veterinary.application.service

import com.petcaresuite.veterinary.application.dto.ResponseDTO
import com.petcaresuite.veterinary.application.dto.VeterinaryDTO
import com.petcaresuite.veterinary.application.dto.VeterinaryFilterDTO
import com.petcaresuite.veterinary.application.mapper.VeterinaryMapper
import com.petcaresuite.veterinary.application.port.input.VeterinaryUseCase
import com.petcaresuite.veterinary.application.port.output.VeterinaryPersistencePort
import com.petcaresuite.veterinary.application.service.messages.Responses
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    override fun save(veterinaryDTO: VeterinaryDTO): ResponseDTO {
        val veterinary = veterinaryMapper.toDomain(veterinaryDTO)
        veterinaryPersistencePort.save(veterinary)
        return ResponseDTO(message = Responses.VETERINARY_CREATED)
    }

    override fun update(veterinaryDTO: VeterinaryDTO): ResponseDTO? {
        val veterinary = veterinaryMapper.toDomain(veterinaryDTO)
        veterinaryPersistencePort.findById(veterinaryDTO.vetId!!)
        veterinaryPersistencePort.update(veterinary)
        return ResponseDTO(message = Responses.VETERINARY_UPDATED)
    }

    override fun getAllByFilterPaginated(filterDTO: VeterinaryFilterDTO, pageable: Pageable): Page<VeterinaryDTO> {
        val filter = veterinaryMapper.toDomain(filterDTO)
        return veterinaryPersistencePort.findAllByFilterPaginated(filter, pageable)
            .map { veterinaryMapper.toDTO(it) }
    }

}