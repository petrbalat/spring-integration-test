package cz.petrbalat.spring_integration.dto

import java.time.LocalDate

data class InputDto(
    val name: String,
    val birth: LocalDate,
)
