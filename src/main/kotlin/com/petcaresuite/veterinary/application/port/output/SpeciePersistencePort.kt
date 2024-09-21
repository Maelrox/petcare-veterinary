package com.petcaresuite.veterinary.application.port.output

import com.petcaresuite.veterinary.domain.model.Specie
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SpeciePersistencePort {

     fun findAll(): List<Specie>

     fun save(specie: Specie): Specie

     fun findAllByFilterPaginated(filter: Specie, pageable: Pageable): Page<Specie>

     fun findById(specieId: Int): Specie

     fun update(specie: Specie): Specie

}