package com.petcaresuite.veterinary.application.port.output

import com.petcaresuite.veterinary.domain.model.Veterinary


interface VeterinaryPersistencePort {

     fun findAll(veterinaryId: Long?, companyId: Long): List<Veterinary>

}