package com.kyotu.challenge.exception.handler;

import com.kyotu.challenge.exception.CityNotFoundException;
import com.kyotu.challenge.exception.DateParseException;
import com.kyotu.challenge.exception.FIleReadException;
import com.kyotu.challenge.exception.TemperatureParseException;
import com.kyotu.challenge.exception.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFoundException(CityNotFoundException exc) {
        ErrorResponse errorResponse = new ErrorResponse(
                exc.getMessage(),
                "The specified city was not found.",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateParseException.class)
    public ResponseEntity<ErrorResponse> handleDateParseException(DateParseException exc) {
        ErrorResponse errorResponse = new ErrorResponse(
                exc.getMessage(),
                "There was an error parsing the date.",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FIleReadException.class)
    public ResponseEntity<ErrorResponse> handleFileReadException(FIleReadException exc) {
        ErrorResponse errorResponse = new ErrorResponse(
                exc.getMessage(),
                "There was an error reading the file.",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TemperatureParseException.class)
    public ResponseEntity<ErrorResponse> handleTemperatureParseException(TemperatureParseException exc) {
        ErrorResponse errorResponse = new ErrorResponse(
                exc.getMessage(),
                "There was an error parsing the temperature.",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
