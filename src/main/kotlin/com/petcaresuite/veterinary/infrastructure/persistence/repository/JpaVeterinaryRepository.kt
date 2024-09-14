package com.petcaresuite.veterinary.infrastructure.persistence.repository

import com.petcaresuite.veterinary.infrastructure.persistence.entity.VeterinaryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaVeterinaryRepository : JpaRepository<VeterinaryEntity, Long> {

    fun findAllByVetIdAndCompanyId(veterinaryId: Long, companyId: Long): List<VeterinaryEntity>

}