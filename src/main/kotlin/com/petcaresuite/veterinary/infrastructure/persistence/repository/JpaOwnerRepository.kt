package com.petcaresuite.veterinary.infrastructure.persistence.repository

import com.petcaresuite.veterinary.domain.model.Owner
import com.petcaresuite.veterinary.infrastructure.persistence.entity.OwnerEntity
import com.petcaresuite.veterinary.infrastructure.persistence.entity.VeterinaryEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaOwnerRepository : JpaRepository<OwnerEntity, Long> {

    @Query("""
        SELECT o FROM OwnerEntity o 
        WHERE (:#{#ownerId} IS NULL OR o.ownerId = :ownerId) 
        AND (:#{#identification} IS NULL OR o.identification = :identification)
        AND o.companyId = :companyId
        AND (
            :#{#name} IS NULL OR LOWER(o.name) LIKE LOWER(CONCAT('%', :#{#name}, '%'))
        )
    """)
    fun findAllByFilter(
        @Param("ownerId") ownerId: Long?,
        @Param("identification") identification: String?,
        @Param("name") name: String?,
        @Param("companyId") companyId: Long
    ): List<OwnerEntity>


    @Query(
        """
            SELECT o FROM OwnerEntity o
            WHERE o.companyId = :#{#filter.companyId}
            AND (:#{#filter.identification} IS NULL OR o.identification = :#{#filter.identification})
            AND (:#{#filter.identificationTypeId} IS NULL OR o.identificationTypeId = :#{#filter.identificationTypeId})
            AND (:#{#filter.address} IS NULL OR o.address = :#{#filter.address})
            AND (:#{#filter.phone} IS NULL OR o.phone = :#{#filter.phone})
            AND (
                :#{#filter.name} IS NULL 
                OR LOWER(o.name) LIKE LOWER(CONCAT('%', :#{#filter.name}, '%'))
            ) 
            ORDER BY o.ownerId desc 
            """
    )
    fun findAllByFilter(filter: Owner, pageable: Pageable): Page<OwnerEntity>
}
