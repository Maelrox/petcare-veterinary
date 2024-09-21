package com.petcaresuite.veterinary.infrastructure.persistence.repository

import com.petcaresuite.veterinary.domain.model.Specie
import com.petcaresuite.veterinary.infrastructure.persistence.entity.SpecieEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface JpaSpecieRepository : JpaRepository<SpecieEntity, Int> {


    @Query(
        """
            SELECT s FROM SpecieEntity s
            WHERE (:#{#filter.name} IS NULL OR s.name = :#{#filter.name})
            ORDER BY s.name asc 
            """
    )
    fun findAllByFilter(filter: Specie, pageable: Pageable): Page<SpecieEntity>

}
