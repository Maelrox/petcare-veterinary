package com.petcaresuite.veterinary.application.service

import com.petcaresuite.veterinary.application.dto.*
import com.petcaresuite.veterinary.application.mapper.OwnerMapper
import com.petcaresuite.veterinary.application.port.input.OwnerUseCase
import com.petcaresuite.veterinary.application.port.output.OwnerPersistencePort
import com.petcaresuite.veterinary.application.service.messages.Responses
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class OwnerService(
    private val ownerPersistencePort: OwnerPersistencePort,
    private val ownerMapper: OwnerMapper,
) :
    OwnerUseCase {

    override fun getAll(ownerId: Long?, identification: String?, name: String?, companyId: Long): List<OwnerDTO> {
        val owners = ownerPersistencePort.findAll(ownerId, identification, name, companyId)
        return ownerMapper.toDTO(owners)
    }

    override fun save(ownerDTO: OwnerDTO): ResponseDTO {
        val veterinary = ownerMapper.toDomain(ownerDTO)
        ownerPersistencePort.save(veterinary)
        return ResponseDTO(message = Responses.OWNER_CREATED)
    }

    override fun update(ownerDTO: OwnerDTO): ResponseDTO? {
        val veterinary = ownerMapper.toDomain(ownerDTO)
        ownerPersistencePort.findById(ownerDTO.ownerId!!)
        ownerPersistencePort.update(veterinary)
        return ResponseDTO(message = Responses.OWNER_UPDATED)
    }

    override fun getAllByFilterPaginated(filterDTO: OwnerFilterDTO, pageable: Pageable): Page<OwnerDTO> {
        val filter = ownerMapper.toDomain(filterDTO)
        return ownerPersistencePort.findAllByFilterPaginated(filter, pageable)
            .map { ownerMapper.toDTO(it) }
    }

}