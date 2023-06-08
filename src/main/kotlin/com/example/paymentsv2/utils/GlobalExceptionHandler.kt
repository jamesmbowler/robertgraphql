package com.example.paymentsv2.utils

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Configuration
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<String> {
        // Customize the error message or response as needed
        val errorMessage = "Access Denied: You don't have sufficient permissions to access this resource."
        return ResponseEntity(errorMessage, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<String> {
        val errorMessage = "Authentication Failed: Please provide valid credentials."
        return ResponseEntity(errorMessage, HttpStatus.UNAUTHORIZED)
    }

}
