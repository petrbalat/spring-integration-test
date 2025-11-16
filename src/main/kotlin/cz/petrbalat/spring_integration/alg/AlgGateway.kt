package cz.petrbalat.spring_integration.alg

import cz.petrbalat.spring_integration.dto.InputDto
import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway


@MessagingGateway(defaultRequestChannel = "inputChannel")
interface AlgGateway {

    @Gateway
    fun start(data: InputDto)
}