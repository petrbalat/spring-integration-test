package cz.petrbalat.spring_integration.alg

import cz.petrbalat.spring_integration.dto.InputDto
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class Alg1Cmp {

    fun validateData(message: Message<InputDto>): Message<InputDto> {
        val data: InputDto = message.payload
        println("-> Filtr 1: Validuji data DTO: ${data.name}")


        // Zde je vaše podmínka.
        val isValid = data.name.contains("Petr")
        return MessageBuilder.withPayload(data)
            .setHeader("isOk", isValid) // Klíčová informace pro router
            .build()
    }

    fun saveResult(message: Message<InputDto>) {
        println("-> Filtr 2: Podmínka splněna. UKLÁDÁM FINÁLNÍ VÝSLEDEK: ${message.payload.name}")
        // Zde by šla vaše logika ukládání do DB
    }
}