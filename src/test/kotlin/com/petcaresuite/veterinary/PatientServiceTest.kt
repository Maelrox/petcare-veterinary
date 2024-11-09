package com.petcaresuite.veterinary

import com.petcaresuite.veterinary.application.dto.*
import com.petcaresuite.veterinary.application.mapper.PatientMapper
import com.petcaresuite.veterinary.application.port.output.PatientPersistencePort
import com.petcaresuite.veterinary.application.service.PatientService
import com.petcaresuite.veterinary.application.service.messages.Responses
import com.petcaresuite.veterinary.domain.model.Owner
import com.petcaresuite.veterinary.domain.model.Patient
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
class PatientServiceTest {

    @Mock
    private lateinit var patientPersistencePort: PatientPersistencePort

    @Mock
    private lateinit var patientMapper: PatientMapper

    private lateinit var patientService: PatientService
    private lateinit var mockPatientDTO: PatientDTO
    private lateinit var mockPatient: Patient
    private lateinit var mockOwner: Owner
    private lateinit var mockOwnerDTO: OwnerDTO
    private lateinit var mockOwnerFilterDTO: OwnerFilterDTO
    private lateinit var mockSpecieDTO: SpecieDTO
    private lateinit var mockSpecie: Specie

    @BeforeEach
    fun setUp() {
        mockOwner = Owner(
            ownerId = 1L,
            identification = "123456789",
            identificationTypeId = 1,
            name = "John Doe",
            address = "123 Main St",
            phone = "1234567890",
            companyId = 1L
        )

        mockOwnerDTO = OwnerDTO(
            ownerId = 1L,
            identification = "123456789",
            identificationTypeId = 1,
            name = "John Doe",
            address = "123 Main St",
            phone = "1234567890",
            companyId = 1L
        )

        mockOwnerFilterDTO = OwnerFilterDTO(
            ownerId = 1L,
            identification = "123456789",
            identificationTypeId = 1,
            name = "John Doe",
            address = "123 Main St",
            phone = "1234567890",
            companyId = 1L
        )

        mockSpecieDTO = SpecieDTO(
            name = "Dog",
            id = 1,
        )

        mockPatientDTO = PatientDTO(
            patientId = 1L,
            name = "Max",
            specie = mockSpecieDTO,
            breed = "Labrador",
            age = "5",
            companyId = 1L,
            owner = mockOwner
        )

        mockSpecie = Specie(
            name = "Dog",
            id = 1,
        )

        mockPatient = Patient(
            patientId = 1L,
            name = "Max",
            breed = "Labrador",
            age = 5,
            companyId = 1L,
            ownerId = 1L,
            specie = mockSpecie,
            owner = mockOwner,
            ownerName = "John Doe"
        )

        patientService = PatientService(patientPersistencePort, patientMapper)
    }

    @Test
    fun `getAll - returns list of patients successfully`() {
        // Given
        val patientId = 1L
        val companyId = 1L
        val patientsList = listOf(mockPatient)
        val patientDTOList = listOf(mockPatientDTO)

        Mockito.`when`(patientPersistencePort.findAll(patientId, companyId))
            .thenReturn(patientsList)
        Mockito.`when`(patientMapper.toDTO(patientsList)).thenReturn(patientDTOList)

        // When
        val result = patientService.getAll(patientId, companyId)

        // Then
        assert(result == patientDTOList)
        Mockito.verify(patientPersistencePort).findAll(patientId, companyId)
        Mockito.verify(patientMapper).toDTO(patientsList)
    }

    @Test
    fun `save - successful patient creation`() {
        // Given
        Mockito.`when`(patientMapper.toDomain(mockPatientDTO)).thenReturn(mockPatient)
        Mockito.`when`(patientPersistencePort.save(mockPatient)).thenReturn(mockPatient)

        // When
        val result = patientService.save(mockPatientDTO)

        // Then
        assert(result.message == Responses.PATIENT_CREATED)
        Mockito.verify(patientMapper).toDomain(mockPatientDTO)
        Mockito.verify(patientPersistencePort).save(mockPatient)
    }

    @Test
    fun `update - successful patient update`() {
        // Given
        Mockito.`when`(patientMapper.toDomain(mockPatientDTO)).thenReturn(mockPatient)
        Mockito.`when`(patientPersistencePort.findById(mockPatient.patientId!!)).thenReturn(mockPatient)
        Mockito.`when`(patientPersistencePort.update(mockPatient)).thenReturn(mockPatient)

        // When
        val result = patientService.update(mockPatientDTO)

        // Then
        assert(result?.message == Responses.PATIENT_UPDATED)
        Mockito.verify(patientMapper).toDomain(mockPatientDTO)
        Mockito.verify(patientPersistencePort).findById(mockPatient.patientId!!)
        Mockito.verify(patientPersistencePort).update(mockPatient)
    }

    @Test
    fun `update - throws exception when patient not found`() {
        // Given
        Mockito.`when`(patientMapper.toDomain(mockPatientDTO)).thenReturn(mockPatient)
        Mockito.`when`(patientPersistencePort.findById(mockPatient.patientId!!))
            .thenThrow(EntityNotFoundException("Patient not found"))

        // When/Then
        assertThrows<EntityNotFoundException> {
            patientService.update(mockPatientDTO)
        }
        Mockito.verify(patientPersistencePort, Mockito.never()).update(safeAny())
    }

    @Test
    fun `getAllByFilterPaginated - returns paged list of patients successfully`() {
        // Given
        val filterDTO = PatientFilterDTO(
            name = null,
            breed = null,
            age = null,
            companyId = 1L,
            ownerId = 1L,
            patientId = 0L,
            species = null,
            owner = mockOwnerFilterDTO,
            ownerName = null
        )
        val pageable = PageRequest.of(0, 10)
        val patientsList = listOf(mockPatient)
        val page: Page<Patient> = PageImpl(patientsList)
        val expectedDTOPage: Page<PatientDTO> = PageImpl(listOf(mockPatientDTO))

        Mockito.`when`(patientMapper.toDomain(filterDTO)).thenReturn(mockPatient)
        Mockito.`when`(patientPersistencePort.findAllByFilterPaginated(mockPatient, pageable)).thenReturn(page)
        Mockito.`when`(patientMapper.toDTO(mockPatient)).thenReturn(mockPatientDTO)

        // When
        val result = patientService.getAllByFilterPaginated(filterDTO, pageable)

        // Then
        assert(result.content == expectedDTOPage.content)
        Mockito.verify(patientMapper).toDomain(filterDTO)
        Mockito.verify(patientPersistencePort).findAllByFilterPaginated(mockPatient, pageable)
    }

    // Null-safe any() matcher implementation
    private fun <T> safeAny(): T {
        Mockito.any<T>()
        @Suppress("UNCHECKED_CAST")
        return null as T
    }
}