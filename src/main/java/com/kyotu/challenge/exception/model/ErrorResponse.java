package com.kyotu.challenge.exception.model;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, String details, HttpStatus status) {
}
