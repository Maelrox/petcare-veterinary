package com.petcaresuite.veterinary.infrastructure.config

import com.petcaresuite.appointment.infrastructure.security.PermissionsInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val permissionsInterceptor: PermissionsInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(permissionsInterceptor)
    }
}
