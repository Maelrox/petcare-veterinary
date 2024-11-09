package com.petcaresuite.veterinary

import com.petcaresuite.veterinary.application.dto.OwnerDTO
import com.petcaresuite.veterinary.application.dto.OwnerFilterDTO
import com.petcaresuite.veterinary.application.mapper.OwnerMapper
import com.petcaresuite.veterinary.application.port.output.OwnerPersistencePort
import com.petcaresuite.veterinary.application.service.OwnerService
import com.petcaresuite.veterinary.application.service.messages.Responses
import com.petcaresuite.veterinary.domain.model.Owner
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
class OwnerServiceTest {

	@Mock
	private lateinit var ownerPersistencePort: OwnerPersistencePort

	@Mock
	private lateinit var ownerMapper: OwnerMapper

	private lateinit var ownerService: OwnerService
	private lateinit var mockOwnerDTO: OwnerDTO
	private lateinit var mockOwner: Owner

	@BeforeEach
	fun setUp() {
		mockOwnerDTO = OwnerDTO(
			ownerId = 1L,
			identification = "123456789",
			identificationTypeId = 1,
			name = "John Doe",
			address = "123 Main St",
			phone = "1234567890",
			companyId = 1L
		)

		mockOwner = Owner(
			ownerId = 1L,
			identification = "123456789",
			identificationTypeId = 1,
			name = "John Doe",
			address = "123 Main St",
			phone = "1234567890",
			companyId = 1L
		)

		ownerService = OwnerService(ownerPersistencePort, ownerMapper)
	}

	@Test
	fun `getAll - returns list of owners successfully`() {
		// Given
		val ownerId = 1L
		val identification = "123456789"
		val name = "John"
		val companyId = 1L
		val ownersList = listOf(mockOwner)
		val ownerDTOList = listOf(mockOwnerDTO)

		Mockito.`when`(ownerPersistencePort.findAll(ownerId, identification, name, companyId))
			.thenReturn(ownersList)
		Mockito.`when`(ownerMapper.toDTO(ownersList)).thenReturn(ownerDTOList)

		// When
		val result = ownerService.getAll(ownerId, identification, name, companyId)

		// Then
		assert(result == ownerDTOList)
		Mockito.verify(ownerPersistencePort).findAll(ownerId, identification, name, companyId)
		Mockito.verify(ownerMapper).toDTO(ownersList)
	}

	@Test
	fun `save - successful owner creation`() {
		// Given
		Mockito.`when`(ownerMapper.toDomain(mockOwnerDTO)).thenReturn(mockOwner)
		Mockito.`when`(ownerPersistencePort.save(mockOwner)).thenReturn(mockOwner)

		// When
		val result = ownerService.save(mockOwnerDTO)

		// Then
		assert(result.message == Responses.OWNER_CREATED)
		Mockito.verify(ownerMapper).toDomain(mockOwnerDTO)
		Mockito.verify(ownerPersistencePort).save(mockOwner)
	}

	@Test
	fun `update - successful owner update`() {
		// Given
		Mockito.`when`(ownerMapper.toDomain(mockOwnerDTO)).thenReturn(mockOwner)
		Mockito.`when`(ownerPersistencePort.findById(mockOwnerDTO.ownerId!!)).thenReturn(mockOwner)
		Mockito.`when`(ownerPersistencePort.update(mockOwner)).thenReturn(mockOwner)

		// When
		val result = ownerService.update(mockOwnerDTO)

		// Then
		assert(result?.message == Responses.OWNER_UPDATED)
		Mockito.verify(ownerMapper).toDomain(mockOwnerDTO)
		Mockito.verify(ownerPersistencePort).findById(mockOwnerDTO.ownerId!!)
		Mockito.verify(ownerPersistencePort).update(mockOwner)
	}

	@Test
	fun `update - throws exception when owner not found`() {
		// Given
		Mockito.`when`(ownerMapper.toDomain(mockOwnerDTO)).thenReturn(mockOwner)
		Mockito.`when`(ownerPersistencePort.findById(mockOwnerDTO.ownerId!!))
			.thenThrow(EntityNotFoundException("Owner not found"))

		// When/Then
		assertThrows<EntityNotFoundException> {
			ownerService.update(mockOwnerDTO)
		}
		Mockito.verify(ownerPersistencePort, Mockito.never()).update(safeAny())
	}

	@Test
	fun `getAllByFilterPaginated - returns paged list of owners successfully`() {
		// Given
		val filterDTO = OwnerFilterDTO(
            identification = "123456789",
            identificationTypeId = 1,
            name = "John",
            address = "123 Main St",
            phone = "1234567890",
            companyId = 1L,
            ownerId = 1L
        )
		val pageable = PageRequest.of(0, 10)
		val ownersList = listOf(mockOwner)
		val page: Page<Owner> = PageImpl(ownersList)
		val expectedDTOPage: Page<OwnerDTO> = PageImpl(listOf(mockOwnerDTO))

		Mockito.`when`(ownerMapper.toDomain(filterDTO)).thenReturn(mockOwner)
		Mockito.`when`(ownerPersistencePort.findAllByFilterPaginated(mockOwner, pageable)).thenReturn(page)
		Mockito.`when`(ownerMapper.toDTO(mockOwner)).thenReturn(mockOwnerDTO)

		// When
		val result = ownerService.getAllByFilterPaginated(filterDTO, pageable)

		// Then
		assert(result.content == expectedDTOPage.content)
		Mockito.verify(ownerMapper).toDomain(filterDTO)
		Mockito.verify(ownerPersistencePort).findAllByFilterPaginated(mockOwner, pageable)
	}

	// Null-safe any() matcher implementation
	private fun <T> safeAny(): T {
		Mockito.any<T>()
		@Suppress("UNCHECKED_CAST")
		return null as T
	}
}