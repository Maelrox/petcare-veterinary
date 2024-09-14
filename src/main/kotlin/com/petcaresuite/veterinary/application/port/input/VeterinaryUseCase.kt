package com.petcaresuite.veterinary.application.port.input

import com.petcaresuite.appointment.application.dto.*

interface VeterinaryUseCase {
    
    fun getAll(veterinaryId: Long, companyId: Long): List<VeterinaryDTO>

}