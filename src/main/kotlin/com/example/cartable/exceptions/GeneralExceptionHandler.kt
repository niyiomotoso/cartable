package com.example.cartable.exceptions

import com.example.cartable.dtos.repsonses.ErrorResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GeneralExceptionHandler {
    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(exception: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse()
        error.status = false
        error.message = exception.message
        error.data = null
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun badExceptionHandler(exception: Exception): ResponseEntity<ErrorResponse> {
        val error = ErrorResponse()
        error.status = false
        error.message = exception.message
        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }
}