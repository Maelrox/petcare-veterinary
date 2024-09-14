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
        WHERE (:patientId IS NULL OR p.patientId = :patientId) 
        AND p.owner.companyId = :companyId
    """)
    fun findAllByPatientIdAndCompanyId(
        @Param("veterinaryId") patientId: Long?,
        @Param("companyId") companyId: Long
    ): List<PatientEntity>

    @Query(
        """
            SELECT p FROM PatientEntity p
            WHERE p.owner.companyId = :#{#filter.companyId}
            AND (:#{#filter.breed} IS NULL OR p.breed = :#{#filter.breed})
            AND (:#{#filter.species} IS NULL OR p.species = :#{#filter.species})
            AND (:#{#filter.ownerId} IS NULL OR p.owner.ownerId = :#{#filter.ownerId})
            AND (
                :#{#filter.name} IS NULL 
                OR LOWER(p.name) LIKE LOWER(CONCAT('%', :#{#filter.name}, '%'))
            ) 
            ORDER BY p.patientId desc 
            """
    )
    fun findAllByFilter(filter: Patient, pageable: Pageable): Page<PatientEntity>
}
