package com.petcaresuite.veterinary.infrastructure.persistence.repository

import com.petcaresuite.veterinary.domain.model.Veterinary
import com.petcaresuite.veterinary.infrastructure.persistence.entity.VeterinaryEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    @Query(
        """
            SELECT v FROM VeterinaryEntity v 
            WHERE v.companyId = :#{#filter.companyId}
            AND (:#{#filter.identification} IS NULL OR v.identification = :#{#filter.identification})
            AND (:#{#filter.identificationType} IS NULL OR v.identificationType.id = COALESCE(:#{#filter.identificationType?.id}, v.identificationType.id))
            AND (:#{#filter.specialization} IS NULL OR v.specialization = :#{#filter.specialization})
            AND (:#{#filter.phone} IS NULL OR v.phone = :#{#filter.phone})
            AND (
                :#{#filter.name} IS NULL 
                OR LOWER(v.name) LIKE LOWER(CONCAT('%', :#{#filter.name}, '%'))
            ) 
            ORDER BY v.vetId desc 
            """
    )
    fun findAllByFilter(filter: Veterinary, pageable: Pageable): Page<VeterinaryEntity>
}
