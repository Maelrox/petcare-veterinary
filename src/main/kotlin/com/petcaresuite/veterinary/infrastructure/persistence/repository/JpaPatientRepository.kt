package com.petcaresuite.veterinary.infrastructure.persistence.repository

import com.petcaresuite.veterinary.domain.model.Patient
import com.petcaresuite.veterinary.infrastructure.persistence.entity.PatientEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JpaPatientRepository : JpaRepository<PatientEntity, Long> {

    @Query("""
        SELECT p FROM PatientEntity p 
        WHERE (:ownerId IS NULL OR p.owner.ownerId = :ownerId) 
        AND p.owner.companyId = :companyId
    """)
    fun findAllByPatientIdAndCompanyId(
        @Param("ownerId") ownerId: Long?,
        @Param("companyId") companyId: Long
    ): List<PatientEntity>

    @Query(
        """
            SELECT p FROM PatientEntity p
            WHERE p.owner.companyId = :#{#filter.companyId}
            AND (:#{#filter.breed} IS NULL OR p.breed = :#{#filter.breed})
            AND (:#{#filter.specie} IS NULL OR p.specie.name = :#{#filter.specie?.name})
            AND (:#{#filter.ownerId} IS NULL OR p.owner.ownerId = :#{#filter.ownerId})
            AND (
                :#{#filter.name} IS NULL 
                OR LOWER(p.name) LIKE LOWER(CONCAT('%', :#{#filter.name}, '%'))
            ) 
            AND (
                :#{#filter.ownerName} IS NULL 
                OR LOWER(p.owner.name) LIKE LOWER(CONCAT('%', :#{#filter.ownerName}, '%'))
            ) 
            ORDER BY p.patientId desc 
            """
    )
    fun findAllByFilter(filter: Patient, pageable: Pageable): Page<PatientEntity>
}
