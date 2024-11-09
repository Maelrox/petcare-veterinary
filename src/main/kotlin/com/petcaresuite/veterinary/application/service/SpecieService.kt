package com.petcaresuite.veterinary.application.service

import com.petcaresuite.veterinary.application.dto.*
import com.petcaresuite.veterinary.application.mapper.SpecieMapper
import com.petcaresuite.veterinary.application.port.input.SpecieUseCase
import com.petcaresuite.veterinary.application.port.output.SpeciePersistencePort
import com.petcaresuite.veterinary.application.service.messages.Responses
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SpecieService(
    private val speciePersistencePort: SpeciePersistencePort,
    private val specieMapper: SpecieMapper,
) :
    SpecieUseCase {

    override fun getAll(): List<SpecieDTO> {
        val owners = speciePersistencePort.findAll()
        return specieMapper.toDTO(owners)
    }

    override fun save(specieDTO: SpecieDTO): ResponseDTO {
        val specie = specieMapper.toDomain(specieDTO)
        speciePersistencePort.save(specie)
        return ResponseDTO(message = Responses.SPECIE_UPDATED)
    }

    override fun update(specieDTO: SpecieDTO): ResponseDTO? {
        val specie = specieMapper.toDomain(specieDTO)
        speciePersistencePort.findById(specie.id!!)
        speciePersistencePort.update(specie)
        return ResponseDTO(message = Responses.SPECIE_UPDATED)
    }

    override fun getAllByFilterPaginated(filterDTO: SpecieFilterDTO, pageable: Pageable): Page<SpecieDTO> {
        val filter = specieMapper.toDomain(filterDTO)
        return speciePersistencePort.findAllByFilterPaginated(filter, pageable)
            .map { specieMapper.toDTO(it) }
    }

}