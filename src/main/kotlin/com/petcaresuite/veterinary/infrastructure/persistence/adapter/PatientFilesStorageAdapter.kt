package com.petcaresuite.veterinary.infrastructure.persistence.adapter

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

    override fun store(file: MultipartFile): Path {
        val targetLocation = Paths.get(uploadDir, file.originalFilename!!)
        try {
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, targetLocation)
            }
        } catch (e: IOException) {
            throw RuntimeException("Failed to store file: ${file.originalFilename}", e)
        }
        return targetLocation
    }

}