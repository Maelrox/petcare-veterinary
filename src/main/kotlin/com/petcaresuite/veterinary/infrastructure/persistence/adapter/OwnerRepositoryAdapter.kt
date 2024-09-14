package com.petcaresuite.veterinary.infrastructure.persistence.adapter

import com.petcaresuite.veterinary.application.port.output.OwnerPersistencePort
import com.petcaresuite.veterinary.application.port.output.VeterinaryPersistencePort
import com.petcaresuite.veterinary.domain.model.Owner
import com.petcaresuite.veterinary.domain.model.Veterinary
import com.petcaresuite.veterinary.infrastructure.persistence.mapper.OwnerEntityMapper
import com.petcaresuite.veterinary.infrastructure.persistence.mapper.VeterinaryEntityMapper
import com.petcaresuite.veterinary.infrastructure.persistence.repository.JpaOwnerRepository
import com.petcaresuite.veterinary.infrastructure.persistence.repository.JpaVeterinaryRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class OwnerRepositoryAdapter(
    private val jpaOwnerRepository: JpaOwnerRepository,
    private val ownerMapper: OwnerEntityMapper
) : OwnerPersistencePort {

    override fun findAll(ownerId: Long?, companyId: Long): List<Owner> {
        val owners = jpaOwnerRepository.findAllByVetIdAndCompanyId(ownerId, companyId)
        return ownerMapper.toDomain(owners)
    }

    override fun findById(ownerId: Long): Owner {
        val ownerEntity = jpaOwnerRepository.findById(ownerId)
            .orElseThrow { EntityNotFoundException("Veterinary with id $ownerId not found") }
        return ownerMapper.toDomain(ownerEntity)
    }

    override fun update(owner: Owner): Owner {
        val ownerEntity = ownerMapper.toEntity(owner)
        jpaOwnerRepository.save(ownerEntity)
        return ownerMapper.toDomain(ownerEntity)
    }

    override fun save(owner: Owner): Owner {
        val ownerEntity = ownerMapper.toEntity(owner)
        jpaOwnerRepository.save(ownerEntity)
        return ownerMapper.toDomain(ownerEntity)
    }

    override fun findAllByFilterPaginated(filter: Owner, pageable: Pageable): Page<Owner> {
        val pagedRolesEntity = jpaOwnerRepository.findAllByFilter(filter, pageable)
        return pagedRolesEntity.map { ownerMapper.toDomain(it) }
    }

}