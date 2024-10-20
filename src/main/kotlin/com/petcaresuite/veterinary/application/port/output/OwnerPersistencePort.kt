package com.petcaresuite.veterinary.application.port.output

import com.petcaresuite.veterinary.domain.model.Owner
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OwnerPersistencePort {

     fun findAll(ownerId: Long?, identification: String?, name: String?, companyId: Long): List<Owner>

     fun save(owner: Owner): Owner

     fun findAllByFilterPaginated(filter: Owner, pageable: Pageable): Page<Owner>

     fun findById(ownerId: Long): Owner

     fun update(owner: Owner): Owner

}