package com.petcaresuite.veterinary.application.port.output

import com.petcaresuite.veterinary.domain.model.Veterinary
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface VeterinaryPersistencePort {

     fun findAll(veterinaryId: Long?, companyId: Long): List<Veterinary>

     fun save(veterinary: Veterinary): Veterinary

     fun findAllByFilterPaginated(filter: Veterinary, pageable: Pageable): Page<Veterinary>

     fun findById(vetId: Long): Veterinary

     fun update(veterinary: Veterinary): Veterinary

}