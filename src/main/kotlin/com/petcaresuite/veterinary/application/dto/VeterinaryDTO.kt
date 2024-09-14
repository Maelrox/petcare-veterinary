package com.petcaresuite.appointment.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class VeterinaryDTO(
    val vetId: Long?,
    val patientId: Long,
    val vetName: String?,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    val appointmentDate: LocalDateTime,
    val reason: String?,
    val status: String?,
    var companyId: Long?
)