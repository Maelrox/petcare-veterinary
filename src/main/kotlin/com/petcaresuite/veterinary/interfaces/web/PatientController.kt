package com.petcaresuite.veterinary.interfaces.web

import com.petcaresuite.appointment.application.service.modules.ModuleActions
import com.petcaresuite.veterinary.application.dto.*
import com.petcaresuite.veterinary.application.port.input.PatientUseCase
import com.petcaresuite.veterinary.application.service.PatientService
import com.petcaresuite.veterinary.application.service.modules.Modules
import com.petcaresuite.veterinary.infrastructure.security.Permissions
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/patient")
class PatientController(
    private val patientUseCase: PatientUseCase,
    private val patientService: PatientService
) {

    @GetMapping()
    @Permissions(Modules.PATIENTS, ModuleActions.VIEW)
    fun getPatients(@RequestParam(required = false) ownerId: Long?, request: HttpServletRequest): ResponseEntity<List<PatientDTO>> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        return ResponseEntity.ok(patientUseCase.getAll(ownerId, companyId))
    }

    @PostMapping()
    @Permissions(Modules.PATIENTS, ModuleActions.CREATE)
    fun savePatient(@RequestBody patientDTO: PatientDTO, request: HttpServletRequest): ResponseEntity<ResponseDTO> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        patientDTO.companyId = companyId
        return ResponseEntity.ok(patientUseCase.save(patientDTO))
    }

    @PutMapping()
    @Permissions(Modules.PATIENTS, ModuleActions.UPDATE)
    fun updatePatient(@RequestBody patientDTO: PatientDTO, request: HttpServletRequest): ResponseEntity<ResponseDTO> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        patientDTO.companyId = companyId
        return ResponseEntity.ok(patientUseCase.update(patientDTO))
    }

    @PutMapping("/{patientId}/patientFiles")
    @Permissions(Modules.PATIENTS, ModuleActions.UPDATE)
    fun attachFile(
        @PathVariable patientId: Long,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("description") description: String,
        request: HttpServletRequest
    ): ResponseEntity<ResponseDTO> {
        val companyId = request.getAttribute("companyId").toString().toLong()
        val response = patientService.attachFile(file, patientId, companyId, description)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{patientId}/patientFiles")
    @Permissions(Modules.PATIENTS, ModuleActions.VIEW)
    fun listFiles(
        @PathVariable patientId: Long,
        request: HttpServletRequest
    ): ResponseEntity<List<PatientFilesDTO>> {
        val companyId = request.getAttribute("companyId").toString().toLong()
        val response = patientService.listPatientFiles(patientId, companyId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/search")
    @Permissions(Modules.PATIENTS, ModuleActions.VIEW)
    fun getAllPermissionsByFilter(@ModelAttribute filterDTO: PatientFilterDTO, @RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "30") size: Int, request: HttpServletRequest): ResponseEntity<PaginatedResponseDTO<PatientDTO>> {
        val pageable = PageRequest.of(page, size)
        val companyId  = request.getAttribute("companyId").toString().toLong()
        filterDTO.companyId = companyId
        val result = patientUseCase.getAllByFilterPaginated(filterDTO, pageable)

        val pageDTO = PageDTO(
            page = result.number,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages
        )

        val paginatedResponse = PaginatedResponseDTO(
            data = result.content,
            pagination = pageDTO
        )

        return ResponseEntity.ok(paginatedResponse)
    }

}