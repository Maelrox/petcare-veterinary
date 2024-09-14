package com.petcaresuite.veterinary.interfaces.web

import com.petcaresuite.appointment.application.service.modules.ModuleActions
import com.petcaresuite.veterinary.application.dto.*
import com.petcaresuite.veterinary.application.port.input.VeterinaryUseCase
import com.petcaresuite.veterinary.application.service.modules.Modules
import com.petcaresuite.veterinary.infrastructure.security.Permissions
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/owner")
class OwnerController(private val veterinaryUseCase: VeterinaryUseCase) {

    @GetMapping()
    @Permissions(Modules.OWNERS, ModuleActions.VIEW)
    fun getVeterinary(@RequestParam(required = false) veterinaryId: Long?, request: HttpServletRequest): ResponseEntity<List<VeterinaryDTO>> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        return ResponseEntity.ok(veterinaryUseCase.getAll(veterinaryId, companyId))
    }

    @PostMapping()
    @Permissions(Modules.OWNERS, ModuleActions.CREATE)
    fun saveVeterinary(@RequestBody veterinaryDTO: VeterinaryDTO, request: HttpServletRequest): ResponseEntity<ResponseDTO> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        veterinaryDTO.companyId = companyId
        return ResponseEntity.ok(veterinaryUseCase.save(veterinaryDTO))
    }

    @PutMapping()
    @Permissions(Modules.OWNERS, ModuleActions.UPDATE)
    fun updateVeterinary(@RequestBody veterinaryDTO: VeterinaryDTO, request: HttpServletRequest): ResponseEntity<ResponseDTO> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        veterinaryDTO.companyId = companyId
        return ResponseEntity.ok(veterinaryUseCase.update(veterinaryDTO))
    }

    @GetMapping("/search")
    @Permissions(Modules.OWNERS, ModuleActions.VIEW)
    fun getAllPermissionsByFilter(@ModelAttribute filterDTO: VeterinaryFilterDTO, @RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "30") size: Int, request: HttpServletRequest): ResponseEntity<PaginatedResponseDTO<VeterinaryDTO>> {
        val pageable = PageRequest.of(page, size)
        val companyId  = request.getAttribute("companyId").toString().toLong()
        filterDTO.companyId = companyId
        val result = veterinaryUseCase.getAllByFilterPaginated(filterDTO, pageable)

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