package com.petcaresuite.veterinary

import com.petcaresuite.veterinary.application.dto.VeterinaryDTO
import com.petcaresuite.veterinary.application.dto.VeterinaryFilterDTO
import com.petcaresuite.veterinary.application.mapper.VeterinaryMapper
import com.petcaresuite.veterinary.application.port.output.VeterinaryPersistencePort
import com.petcaresuite.veterinary.application.service.VeterinaryService
import com.petcaresuite.veterinary.application.service.messages.Responses
import com.petcaresuite.veterinary.domain.model.Veterinary
import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@ExtendWith(MockitoExtension::class)
class VeterinaryServiceTest {

    @Mock
    private lateinit var veterinaryPersistencePort: VeterinaryPersistencePort

    @Mock
    private lateinit var veterinaryMapper: VeterinaryMapper

    private lateinit var veterinaryService: VeterinaryService
    private lateinit var mockVeterinaryDTO: VeterinaryDTO
    private lateinit var mockVeterinary: Veterinary

    @BeforeEach
    fun setUp() {
        mockVeterinaryDTO = VeterinaryDTO(
            vetId = 1L,
            identification = "V123456789",
            identificationTypeId = 1,
            name = "Dr. John Smith",
            phone = "1234567890",
            specialization = "Small Animals",
            companyId = 1L
        )

        mockVeterinary = Veterinary(
            vetId = 1L,
            identification = "V123456789",
            identificationTypeId = 1,
            name = "Dr. John Smith",
            phone = "1234567890",
            specialization = "Small Animals",
            companyId = 1L
        )

        veterinaryService = VeterinaryService(veterinaryPersistencePort, veterinaryMapper)
    }

    @Test
    fun `getAll - returns list of veterinaries successfully`() {
        // Given
        val veterinaryId = 1L
        val companyId = 1L
        val veterinariesList = listOf(mockVeterinary)
        val veterinaryDTOList = listOf(mockVeterinaryDTO)

        Mockito.`when`(veterinaryPersistencePort.findAll(veterinaryId, companyId))
            .thenReturn(veterinariesList)
        Mockito.`when`(veterinaryMapper.toDTO(veterinariesList)).thenReturn(veterinaryDTOList)

        // When
        val result = veterinaryService.getAll(veterinaryId, companyId)

        // Then
        assert(result == veterinaryDTOList)
        Mockito.verify(veterinaryPersistencePort).findAll(veterinaryId, companyId)
        Mockito.verify(veterinaryMapper).toDTO(veterinariesList)
    }

    @Test
    fun `save - successful veterinary creation`() {
        // Given
        Mockito.`when`(veterinaryMapper.toDomain(mockVeterinaryDTO)).thenReturn(mockVeterinary)
        Mockito.`when`(veterinaryPersistencePort.save(mockVeterinary)).thenReturn(mockVeterinary)

        // When
        val result = veterinaryService.save(mockVeterinaryDTO)

        // Then
        assert(result.message == Responses.VETERINARY_CREATED)
        Mockito.verify(veterinaryMapper).toDomain(mockVeterinaryDTO)
        Mockito.verify(veterinaryPersistencePort).save(mockVeterinary)
    }

    @Test
    fun `update - successful veterinary update`() {
        // Given
        Mockito.`when`(veterinaryMapper.toDomain(mockVeterinaryDTO)).thenReturn(mockVeterinary)
        Mockito.`when`(veterinaryPersistencePort.findById(mockVeterinaryDTO.vetId!!)).thenReturn(mockVeterinary)
        Mockito.`when`(veterinaryPersistencePort.update(mockVeterinary)).thenReturn(mockVeterinary)

        // When
        val result = veterinaryService.update(mockVeterinaryDTO)

        // Then
        assert(result?.message == Responses.VETERINARY_UPDATED)
        Mockito.verify(veterinaryMapper).toDomain(mockVeterinaryDTO)
        Mockito.verify(veterinaryPersistencePort).findById(mockVeterinaryDTO.vetId!!)
        Mockito.verify(veterinaryPersistencePort).update(mockVeterinary)
    }

    @Test
    fun `update - throws exception when veterinary not found`() {
        // Given
        Mockito.`when`(veterinaryMapper.toDomain(mockVeterinaryDTO)).thenReturn(mockVeterinary)
        Mockito.`when`(veterinaryPersistencePort.findById(mockVeterinaryDTO.vetId!!))
            .thenThrow(EntityNotFoundException("Veterinary not found"))

        // When/Then
        assertThrows<EntityNotFoundException> {
            veterinaryService.update(mockVeterinaryDTO)
        }
        Mockito.verify(veterinaryPersistencePort, Mockito.never()).update(safeAny())
    }

    @Test
    fun `getAllByFilterPaginated - returns paged list of veterinaries successfully`() {
        // Given
        val filterDTO = VeterinaryFilterDTO(
            vetId = 1L,
            identification = "V123456789",
            identificationTypeId = 1,
            name = "Dr. John",
            phone = "1234567890",
            specialization = "Small Animals",
            companyId = 1L
        )
        val pageable = PageRequest.of(0, 10)
        val veterinariesList = listOf(mockVeterinary)
        val page: Page<Veterinary> = PageImpl(veterinariesList)
        val expectedDTOPage: Page<VeterinaryDTO> = PageImpl(listOf(mockVeterinaryDTO))

        Mockito.`when`(veterinaryMapper.toDomain(filterDTO)).thenReturn(mockVeterinary)
        Mockito.`when`(veterinaryPersistencePort.findAllByFilterPaginated(mockVeterinary, pageable)).thenReturn(page)
        Mockito.`when`(veterinaryMapper.toDTO(mockVeterinary)).thenReturn(mockVeterinaryDTO)

        // When
        val result = veterinaryService.getAllByFilterPaginated(filterDTO, pageable)

        // Then
        assert(result.content == expectedDTOPage.content)
        Mockito.verify(veterinaryMapper).toDomain(filterDTO)
        Mockito.verify(veterinaryPersistencePort).findAllByFilterPaginated(mockVeterinary, pageable)
    }

    // Null-safe any() matcher implementation
    private fun <T> safeAny(): T {
        Mockito.any<T>()
        @Suppress("UNCHECKED_CAST")
        return null as T
    }
}