package com.petcaresuite.veterinary.domain.service

import com.petcaresuite.veterinary.application.port.output.OwnerPersistencePort
import org.springframework.stereotype.Service

@Service
class OwnerDomainService(private val ownerPersistencePort: OwnerPersistencePort) {

    fun validateAppointment(name: String, id: Long) {

    }

}