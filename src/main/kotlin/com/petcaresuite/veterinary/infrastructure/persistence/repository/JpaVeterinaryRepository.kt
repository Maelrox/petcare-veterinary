package com.petcaresuite.veterinary.infrastructure.persistence.repository

import com.petcaresuite.veterinary.infrastructure.persistence.entity.VeterinaryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaVeterinaryRepository : JpaRepository<VeterinaryEntity, Long> {

    @Query("""
        SELECT v FROM VeterinaryEntity v 
        WHERE (:veterinaryId IS NULL OR v.vetId = :veterinaryId) 
        AND v.companyId = :companyId
    """)
    fun findAllByVetIdAndCompanyId(
        @Param("veterinaryId") veterinaryId: Long?,
        @Param("companyId") companyId: Long
    ): List<VeterinaryEntity>
}
