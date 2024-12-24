package com.petcaresuite.veterinary.application.service

import com.petcaresuite.veterinary.application.dto.*
import com.petcaresuite.veterinary.application.mapper.PatientMapper
import com.petcaresuite.veterinary.application.port.input.PatientUseCase
import com.petcaresuite.veterinary.application.port.output.PatientFilesPersistencePort
import com.petcaresuite.veterinary.application.port.output.PatientFilesStoragePort
import com.petcaresuite.veterinary.application.port.output.PatientPersistencePort
import com.petcaresuite.veterinary.application.service.messages.Responses
import com.petcaresuite.veterinary.domain.model.PatientFiles
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class PatientService(
    private val patientPersistencePort: PatientPersistencePort,
    private val patientFilesPersistencePort: PatientFilesPersistencePort,
    private val patientFilesStoragePort: PatientFilesStoragePort,
    private val patientMapper: PatientMapper,
) :
    PatientUseCase {

    override fun getAll(patientId: Long?, companyId: Long): List<PatientDTO> {
        val owners = patientPersistencePort.findAll(patientId, companyId)
        return patientMapper.toDTO(owners)
    }

    override fun save(patientDTO: PatientDTO): ResponseDTO {
        val patient = patientMapper.toDomain(patientDTO)
        patientPersistencePort.save(patient)
        return ResponseDTO(message = Responses.PATIENT_CREATED)
    }

    override fun update(patientDTO: PatientDTO): ResponseDTO? {
        val patient = patientMapper.toDomain(patientDTO)
        patientPersistencePort.findById(patient.patientId!!)
        patientPersistencePort.update(patient)
        return ResponseDTO(message = Responses.PATIENT_UPDATED)
    }

    override fun getAllByFilterPaginated(filterDTO: PatientFilterDTO, pageable: Pageable): Page<PatientDTO> {
        val filter = patientMapper.toDomain(filterDTO)
        return patientPersistencePort.findAllByFilterPaginated(filter, pageable)
            .map { patientMapper.toDTO(it) }
    }

    override fun attachFile(file: MultipartFile, patientId: Long, companyId: Long, description: String): ResponseDTO? {
        val patient = patientPersistencePort.findById(patientId)
        val filePath = patientFilesStoragePort.store(file)
        val patientFiles = PatientFiles(
            0,
            patient = patient,
            companyId = companyId,
            description = description,
            filePath = filePath.toAbsolutePath().toString()
        )
        patientFilesPersistencePort.save(patientFiles)
        return ResponseDTO(message = Responses.PATIENT_UPDATED)

    }

}