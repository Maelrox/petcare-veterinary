package com.petcaresuite.veterinary.interfaces.web

import com.petcaresuite.appointment.application.service.modules.ModuleActions
import com.petcaresuite.veterinary.application.dto.ResponseDTO
import com.petcaresuite.veterinary.application.dto.VeterinaryDTO
import com.petcaresuite.veterinary.application.port.input.VeterinaryUseCase
import com.petcaresuite.veterinary.application.service.modules.Modules
import com.petcaresuite.veterinary.infrastructure.security.Permissions
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping()
class VeterinaryController(private val veterinaryUseCase: VeterinaryUseCase) {

    @GetMapping()
    @Permissions(Modules.VETERINARY, ModuleActions.VIEW)
    fun getVeterinary(@RequestParam(required = false) veterinaryId: Long?, request: HttpServletRequest): ResponseEntity<List<VeterinaryDTO>> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        return ResponseEntity.ok(veterinaryUseCase.getAll(veterinaryId, companyId))
    }

    @PostMapping()
    @Permissions(Modules.VETERINARY, ModuleActions.CREATE)
    fun saveVeterinary(@RequestBody veterinaryDTO: VeterinaryDTO, request: HttpServletRequest): ResponseEntity<ResponseDTO> {
        val companyId  = request.getAttribute("companyId").toString().toLong()
        veterinaryDTO.companyId = companyId
        return ResponseEntity.ok(veterinaryUseCase.save(veterinaryDTO))
    }

}