package com.petcaresuite.veterinary.infrastructure.storage

import com.petcaresuite.veterinary.application.port.output.PatientFilesStoragePort
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Component
class PatientFilesStorageAdapter(
) : PatientFilesStoragePort {

    @Value("\${file.upload-dir}")
    private lateinit var uploadDir: String

    @PostConstruct
    fun init() {
        val path = Paths.get(uploadDir)
        if (!Files.exists(path)) {
            Files.createDirectories(path)
        }
    }

    override fun store(file: MultipartFile, companyId: Long, patientId: Long): Path {
        val targetDir = Paths.get(uploadDir, companyId.toString(), patientId.toString())
        try {
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir)
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed to create directory: $targetDir", e)
        }
        val targetLocation = targetDir.resolve(file.originalFilename!!)
        try {
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, targetLocation)
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed to store file: ${file.originalFilename} in $targetDir", e)
        }
        return targetLocation
    }

}