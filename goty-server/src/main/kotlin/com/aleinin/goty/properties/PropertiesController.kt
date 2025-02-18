package com.aleinin.goty.properties

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@CrossOrigin
@RestController
class PropertiesController(
    private val propertiesService: PropertiesService
) {

    @GetMapping("/properties")
    fun getProperties() = propertiesService.getProperties()

    @PutMapping("/properties")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    fun putProperties(@RequestBody request: Properties) = propertiesService.replaceProperties(request)
}
