package com.atorres.nttdata.debitmsf.controller;

import com.atorres.nttdata.debitmsf.exception.CustomException;
import com.atorres.nttdata.debitmsf.exception.ErrorDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ControllerAdvice {
    /**
     * Metodo que captura una excepcion personalizada
     * @param ex excepcion
     * @return errordto
     */
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorDto> customExceptionHandler(CustomException ex){
        ErrorDto error =  new ErrorDto();
        error.setHttpStatus(ex.getStatus());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error,ex.getStatus());
    }

    /**
     * Metodo por si falla al conectarse el client Feign
     * @param ex excepcion
     * @return errordto
     */
    @ExceptionHandler(value = WebClientRequestException.class)
    public ResponseEntity<ErrorDto> webClientResponseHandler(WebClientRequestException ex){
        ErrorDto error =  new ErrorDto();
        String errorMessage = "Error en la solicitud: " + ex.getMessage();
        error.setHttpStatus(HttpStatus.BAD_REQUEST);
        error.setMessage(errorMessage);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<ErrorDto> handleResponseStatusException(ResponseStatusException ex){
        HttpStatus httpStatus = HttpStatus.valueOf(ex.getStatusCode().value());
        // Obtener el cuerpo (body) de la respuesta desde el ServerHttpResponse
        String responseBody = ex.getReason();
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorDto errorDto = new ErrorDto();
        try {
            errorDto = objectMapper.readValue(responseBody, ErrorDto.class);
        } catch (JsonProcessingException e) {
            errorDto.setMessage(e.getMessage());
            return ResponseEntity.status(httpStatus).body(errorDto);
        }
        return ResponseEntity.status(httpStatus).body(errorDto);
    }
}
