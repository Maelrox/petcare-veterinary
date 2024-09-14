package com.petcaresuite.veterinary.domain.service

import com.petcaresuite.veterinary.application.port.output.VeterinaryPersistencePort
import org.springframework.stereotype.Service

@Service
class VeterinaryDomainService(private val veterinaryPersistencePort : VeterinaryPersistencePort) {

    fun validateAppointment(name: String, id: Long) {

    }

}