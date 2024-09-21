package com.petcaresuite.veterinary.interfaces.web

import com.petcaresuite.appointment.application.service.modules.ModuleActions
import com.petcaresuite.veterinary.application.dto.*
import com.petcaresuite.veterinary.application.port.input.SpecieUseCase
import com.petcaresuite.veterinary.application.service.modules.Modules
import com.petcaresuite.veterinary.infrastructure.security.Permissions
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/specie")
class SpecieController(private val specieUseCase: SpecieUseCase) {

    @GetMapping()
    @Permissions(Modules.PATIENTS, ModuleActions.VIEW)
    fun getSpecies(): ResponseEntity<List<SpecieDTO>> {
        return ResponseEntity.ok(specieUseCase.getAll())
    }

    @PostMapping()
    @Permissions(Modules.PATIENTS, ModuleActions.CREATE)
    fun savePatient(@RequestBody specieDTO: SpecieDTO, request: HttpServletRequest): ResponseEntity<ResponseDTO> {
        return ResponseEntity.ok(specieUseCase.save(specieDTO))
    }

    @PutMapping()
    @Permissions(Modules.PATIENTS, ModuleActions.UPDATE)
    fun updatePatient(@RequestBody specieDTO: SpecieDTO): ResponseEntity<ResponseDTO> {
        return ResponseEntity.ok(specieUseCase.update(specieDTO))
    }

    @GetMapping("/search")
    @Permissions(Modules.PATIENTS, ModuleActions.VIEW)
    fun getAllPermissionsByFilter(@ModelAttribute filterDTO: SpecieFilterDTO, @RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "30") size: Int, request: HttpServletRequest): ResponseEntity<PaginatedResponseDTO<SpecieDTO>> {
        val pageable = PageRequest.of(page, size)
        val result = specieUseCase.getAllByFilterPaginated(filterDTO, pageable)

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