package cz.petrbalat.spring_integration.config

import cz.petrbalat.spring_integration.alg.Alg1Cmp
import cz.petrbalat.spring_integration.dto.InputDto
import cz.petrbalat.spring_integration.dto.OutputDto
import cz.petrbalat.spring_integration.utils.result
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.handler.LoggingHandler
import org.springframework.messaging.Message

@Configuration
class PipelineConfig {
    /**
     * Hlavní tok (Pipeline)
     */
    @Bean
    fun mainPipelineFlow(alg: Alg1Cmp): IntegrationFlow {
        return IntegrationFlow.from("inputChannel") // Vstupní kanál
            // KROK 1: Volání vašeho prvního algoritmu (Validace)
            // Metoda vrátí zprávu s headerem "isOk"

            .handle( { it: Message<InputDto> ->
                alg.validateData(it)
            })
            .route(Message::class.java, { m -> m.result }, { mapping ->
                mapping
                    .channelMapping(true, FINAL_SAVE_CHANNEL) // Pokud je FALSE, pošli ji do kanálu pro přeskočení
                    .channelMapping(false, SKIP_CHANNEL)
            })
            .get()
    }

    /**
     * Tok pro větev ULOŽENÍ VÝSLEDKU
     */
    @Bean
    fun finalSaveFlow(alg: Alg1Cmp): IntegrationFlow {
        return IntegrationFlow.from(FINAL_SAVE_CHANNEL)
            .handle( { it: Message<OutputDto> ->
                alg.saveResult(it)
            })
            .get()
    }

    /**
     * Tok pro větev PŘESKOČENÍ
     */
    @Bean
    fun skipFlow(): IntegrationFlow {
        return IntegrationFlow.from(SKIP_CHANNEL)
            .log(LoggingHandler.Level.WARN, "Proces přeskočen - nevalidní vstup.")
            .get()
    }
}

// Kanály pro větve routeru
const val FINAL_SAVE_CHANNEL: String = "finalSaveChannel"
const val SKIP_CHANNEL: String = "skipChannel"