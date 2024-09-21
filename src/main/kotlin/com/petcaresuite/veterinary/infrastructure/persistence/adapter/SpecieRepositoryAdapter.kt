package com.petcaresuite.veterinary.infrastructure.persistence.adapter

import com.petcaresuite.veterinary.application.port.output.SpeciePersistencePort
import com.petcaresuite.veterinary.domain.model.Specie
import com.petcaresuite.veterinary.infrastructure.persistence.mapper.SpecieEntityMapper
import com.petcaresuite.veterinary.infrastructure.persistence.repository.JpaSpecieRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class SpecieRepositoryAdapter(
    private val jpaSpecieRepository: JpaSpecieRepository,
    private val specieMapper: SpecieEntityMapper
) : SpeciePersistencePort {

    override fun findAll(): List<Specie> {
        val species = jpaSpecieRepository.findAll()
        return specieMapper.toDomain(species)
    }

    override fun findById(spieceId: Int): Specie {
        val spieceEntity = jpaSpecieRepository.findById(spieceId)
            .orElseThrow { EntityNotFoundException("Specie with id $spieceId not found") }
        return specieMapper.toDomain(spieceEntity)
    }

    override fun update(spiece: Specie): Specie {
        val spieceEntity = specieMapper.toEntity(spiece)
        jpaSpecieRepository.save(spieceEntity)
        return specieMapper.toDomain(spieceEntity)
    }

    override fun save(specie: Specie): Specie {
        val spieceEntity = specieMapper.toEntity(specie)
        jpaSpecieRepository.save(spieceEntity)
        return specieMapper.toDomain(spieceEntity)
    }

    override fun findAllByFilterPaginated(filter: Specie, pageable: Pageable): Page<Specie> {
        val pagedRolesEntity = jpaSpecieRepository.findAllByFilter(filter, pageable)
        return pagedRolesEntity.map { specieMapper.toDomain(it) }
    }

}