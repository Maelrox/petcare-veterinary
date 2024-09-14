package com.petcaresuite.veterinary.infrastructure.persistence.adapter

import com.petcaresuite.veterinary.application.port.output.VeterinaryPersistencePort
import com.petcaresuite.veterinary.domain.model.Veterinary
import com.petcaresuite.veterinary.infrastructure.persistence.mapper.VeterinaryEntityMapper
import com.petcaresuite.veterinary.infrastructure.persistence.repository.JpaVeterinaryRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class VeterinaryRepositoryAdapter(
    private val jpaVeterinaryRepository: JpaVeterinaryRepository,
    private val veterinaryMapper: VeterinaryEntityMapper
) : VeterinaryPersistencePort {

    override fun findAll(veterinaryId: Long?, companyId: Long): List<Veterinary> {
        val veterinaries = jpaVeterinaryRepository.findAllByVetIdAndCompanyId(veterinaryId, companyId)
        return veterinaryMapper.toDomain(veterinaries)
    }

    override fun findById(vetId: Long): Veterinary {
        val veterinaryEntity = jpaVeterinaryRepository.findById(vetId)
            .orElseThrow { EntityNotFoundException("Veterinary with id $vetId not found") }
        return veterinaryMapper.toDomain(veterinaryEntity)
    }

    override fun update(veterinary: Veterinary): Veterinary {
        val veterinaryEntity = veterinaryMapper.toEntity(veterinary)
        jpaVeterinaryRepository.save(veterinaryEntity)
        return veterinaryMapper.toDomain(veterinaryEntity)
    }

    override fun save(veterinary: Veterinary): Veterinary {
        val veterinaryEntity = veterinaryMapper.toEntity(veterinary)
        jpaVeterinaryRepository.save(veterinaryEntity)
        return veterinaryMapper.toDomain(veterinaryEntity)
    }

    override fun findAllByFilterPaginated(filter: Veterinary, pageable: Pageable): Page<Veterinary> {
        val pagedRolesEntity = jpaVeterinaryRepository.findAllByFilter(filter, pageable)
        return pagedRolesEntity.map { veterinaryMapper.toDomain(it) }
    }

}