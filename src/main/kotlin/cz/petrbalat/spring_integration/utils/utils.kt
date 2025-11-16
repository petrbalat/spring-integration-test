package cz.petrbalat.spring_integration.utils

import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder

/**
 * dsl pro vytvoření message
 */
fun <T : Any> message(
    payload: T,
    result: Boolean = true,
    param: MessageBuilder<T>.() -> Unit = {}
): Message<T> {
    val builder = MessageBuilder.withPayload(payload)
        .setHeader(RESULT, result)
    builder.param()
    return builder.build()
}

val Message<*>.result: Boolean? get() = headers[RESULT] as? Boolean


private const val RESULT = "result";