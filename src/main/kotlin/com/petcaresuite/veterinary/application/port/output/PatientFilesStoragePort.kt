package com.petcaresuite.veterinary.application.port.output

import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path

interface PatientFilesStoragePort {

     fun store(file: MultipartFile): Path

}