package cz.petrbalat.spring_integration.alg

import cz.petrbalat.spring_integration.dto.InputDto
import cz.petrbalat.spring_integration.dto.OutputDto
import cz.petrbalat.spring_integration.utils.message
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Period

@Component
class Alg1Cmp {

    fun validateData(message: Message<InputDto>): Message<Any> {
        val data: InputDto = message.payload
        println("-> Filtr 1: Validuji data DTO: ${data.name}")


        // Zde je vaše podmínka.
        val isValid = data.name.contains("Petr")
        if (!isValid) {
            return message(data, result = false) {
                setHeader("Name is ugly", data.name)
            }
        }

        val age = Period.between(data.birth, LocalDate.now()).years
        return message(OutputDto(data.name, age))
    }

    fun saveResult(message: Message<OutputDto>) {
        println("-> Filtr 2: Podmínka splněna. UKLÁDÁM FINÁLNÍ VÝSLEDEK: ${message.payload.name}")
        // Zde by šla vaše logika ukládání do DB
    }
}