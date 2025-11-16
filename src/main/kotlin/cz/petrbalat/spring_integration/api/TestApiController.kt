package cz.petrbalat.spring_integration.api

import cz.petrbalat.spring_integration.alg.AlgGateway
import cz.petrbalat.spring_integration.dto.InputDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RequestMapping("/api/test")
@RestController
class TestApiController(
    private val algGateway: AlgGateway,
) {

    @GetMapping("/{name}")
    fun test(@PathVariable name: String, @RequestParam birth: LocalDate?): Unit {
        val dto = InputDto(name, birth ?: LocalDate.now())
        algGateway.start(dto);
    }

}