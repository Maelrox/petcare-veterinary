package com.petcaresuite.veterinary.interfaces.web

import com.petcaresuite.appointment.application.service.modules.ModuleActions
import com.petcaresuite.veterinary.application.dto.*
import com.petcaresuite.veterinary.application.port.input.OwnerUseCase
import com.petcaresuite.veterinary.application.service.modules.Modules
import com.petcaresuite.veterinary.infrastructure.security.Permissions
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/owner")
class OwnerController(private val ownerUseCase: OwnerUseCase) {

    @GetMapping()
    @Permissions(Modules.OWNERS, ModuleActions.VIEW)
    fun getOwner(@RequestParam(required = false) ownerId: Long?, request: HttpServletRequest): ResponseEntity<List<OwnerDTO>> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        return ResponseEntity.ok(ownerUseCase.getAll(ownerId, companyId))
    }

    @PostMapping()
    @Permissions(Modules.OWNERS, ModuleActions.CREATE)
    fun saveOwner(@RequestBody ownerDTO: OwnerDTO, request: HttpServletRequest): ResponseEntity<ResponseDTO> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        ownerDTO.companyId = companyId
        return ResponseEntity.ok(ownerUseCase.save(ownerDTO))
    }

    @PutMapping()
    @Permissions(Modules.OWNERS, ModuleActions.UPDATE)
    fun updateOwner(@RequestBody ownerDTO: OwnerDTO, request: HttpServletRequest): ResponseEntity<ResponseDTO> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        ownerDTO.companyId = companyId
        return ResponseEntity.ok(ownerUseCase.update(ownerDTO))
    }

    @GetMapping("/search")
    @Permissions(Modules.OWNERS, ModuleActions.VIEW)
    fun getAllPermissionsByFilter(@ModelAttribute filterDTO: OwnerFilterDTO, @RequestParam(defaultValue = "0") page: Int, @RequestParam(defaultValue = "30") size: Int, request: HttpServletRequest): ResponseEntity<PaginatedResponseDTO<OwnerDTO>> {
        val pageable = PageRequest.of(page, size)
        val companyId  = request.getAttribute("companyId").toString().toLong()
        filterDTO.companyId = companyId
        val result = ownerUseCase.getAllByFilterPaginated(filterDTO, pageable)

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