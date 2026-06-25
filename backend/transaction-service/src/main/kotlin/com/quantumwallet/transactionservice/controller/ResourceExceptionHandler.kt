package com.quantumwallet.transactionservice.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import jakarta.servlet.http.HttpServletRequest
import java.time.LocalDateTime

@RestControllerAdvice
class ResourceExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgument(comut: IllegalArgumentException, request: HttpServletRequest): ResponseEntity<CustomError> {
        val status = HttpStatus.BAD_REQUEST // Transforma o erro 500 em 400!
        val error = CustomError(
            timestamp = LocalDateTime.now(),
            status = status.value(),
            error = "Bad Request",
            message = comut.message ?: "Invalid argument",
            path = request.requestURI
        )
        return ResponseEntity.status(status).body(error)
    }
}

// Uma classe simples de apoio para formatar o JSON de resposta
data class CustomError(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val path: String
)