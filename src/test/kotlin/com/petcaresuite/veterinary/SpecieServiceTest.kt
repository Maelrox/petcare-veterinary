package com.petcaresuite.veterinary

import com.petcaresuite.veterinary.application.dto.SpecieDTO
import com.petcaresuite.veterinary.application.dto.SpecieFilterDTO
import com.petcaresuite.veterinary.application.mapper.SpecieMapper
import com.petcaresuite.veterinary.application.port.output.SpeciePersistencePort
import com.petcaresuite.veterinary.application.service.SpecieService
import com.petcaresuite.veterinary.application.service.messages.Responses
import com.petcaresuite.veterinary.domain.model.Specie
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
class SpecieServiceTest {

    @Mock
    private lateinit var speciePersistencePort: SpeciePersistencePort

    @Mock
    private lateinit var specieMapper: SpecieMapper

    private lateinit var specieService: SpecieService
    private lateinit var mockSpecieDTO: SpecieDTO
    private lateinit var mockSpecie: Specie

    @BeforeEach
    fun setUp() {
        mockSpecieDTO = SpecieDTO(
            id = 1,
            name = "Dog"
        )

        mockSpecie = Specie(
            id = 1,
            name = "Dog"
        )

        specieService = SpecieService(speciePersistencePort, specieMapper)
    }

    @Test
    fun `getAll - returns list of species successfully`() {
        // Given
        val speciesList = listOf(mockSpecie)
        val specieDTOList = listOf(mockSpecieDTO)

        Mockito.`when`(speciePersistencePort.findAll())
            .thenReturn(speciesList)
        Mockito.`when`(specieMapper.toDTO(speciesList)).thenReturn(specieDTOList)

        // When
        val result = specieService.getAll()

        // Then
        assert(result == specieDTOList)
        Mockito.verify(speciePersistencePort).findAll()
        Mockito.verify(specieMapper).toDTO(speciesList)
    }

    @Test
    fun `save - successful specie creation`() {
        // Given
        Mockito.`when`(specieMapper.toDomain(mockSpecieDTO)).thenReturn(mockSpecie)
        Mockito.`when`(speciePersistencePort.save(mockSpecie)).thenReturn(mockSpecie)

        // When
        val result = specieService.save(mockSpecieDTO)

        // Then
        assert(result.message == Responses.SPECIE_UPDATED)
        Mockito.verify(specieMapper).toDomain(mockSpecieDTO)
        Mockito.verify(speciePersistencePort).save(mockSpecie)
    }

    @Test
    fun `update - successful specie update`() {
        // Given
        Mockito.`when`(specieMapper.toDomain(mockSpecieDTO)).thenReturn(mockSpecie)
        Mockito.`when`(speciePersistencePort.findById(mockSpecieDTO.id!!)).thenReturn(mockSpecie)
        Mockito.`when`(speciePersistencePort.update(mockSpecie)).thenReturn(mockSpecie)

        // When
        val result = specieService.update(mockSpecieDTO)

        // Then
        assert(result?.message == Responses.SPECIE_UPDATED)
        Mockito.verify(specieMapper).toDomain(mockSpecieDTO)
        Mockito.verify(speciePersistencePort).findById(mockSpecieDTO.id!!)
        Mockito.verify(speciePersistencePort).update(mockSpecie)
    }

    @Test
    fun `update - throws exception when specie not found`() {
        // Given
        Mockito.`when`(specieMapper.toDomain(mockSpecieDTO)).thenReturn(mockSpecie)
        Mockito.`when`(speciePersistencePort.findById(mockSpecieDTO.id!!))
            .thenThrow(EntityNotFoundException("Specie not found"))

        // When/Then
        assertThrows<EntityNotFoundException> {
            specieService.update(mockSpecieDTO)
        }
        Mockito.verify(speciePersistencePort, Mockito.never()).update(safeAny())
    }

    @Test
    fun `getAllByFilterPaginated - returns paged list of species successfully`() {
        // Given
        val filterDTO = SpecieFilterDTO(
            id = 1,
            name = "Dog"
        )
        val pageable = PageRequest.of(0, 10)
        val speciesList = listOf(mockSpecie)
        val page: Page<Specie> = PageImpl(speciesList)
        val expectedDTOPage: Page<SpecieDTO> = PageImpl(listOf(mockSpecieDTO))

        Mockito.`when`(specieMapper.toDomain(filterDTO)).thenReturn(mockSpecie)
        Mockito.`when`(speciePersistencePort.findAllByFilterPaginated(mockSpecie, pageable)).thenReturn(page)
        Mockito.`when`(specieMapper.toDTO(mockSpecie)).thenReturn(mockSpecieDTO)

        // When
        val result = specieService.getAllByFilterPaginated(filterDTO, pageable)

        // Then
        assert(result.content == expectedDTOPage.content)
        Mockito.verify(specieMapper).toDomain(filterDTO)
        Mockito.verify(speciePersistencePort).findAllByFilterPaginated(mockSpecie, pageable)
    }

    // Null-safe any() matcher implementation
    private fun <T> safeAny(): T {
        Mockito.any<T>()
        @Suppress("UNCHECKED_CAST")
        return null as T
    }
}