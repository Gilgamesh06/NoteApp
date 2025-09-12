package com.gilgamesh06.NoteApp.controller;

import com.gilgamesh06.NoteApp.exception.NotaNotFoundException;
import com.gilgamesh06.NoteApp.model.dto.exception.ExceptionDTO;
import com.gilgamesh06.NoteApp.model.dto.exception.ValidExceptionDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que centraliza el manejo de excepciones
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Metodo que maneja la excepciones de DTO anotados con @Valid
     * @param ex MethodArgumentNotValidException
     * @return retorna un ValidExceptionDTO que contiene el mapa de Excepciones
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidExceptionDTO> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(error.getField(), error.getDefaultMessage())
        );
        ValidExceptionDTO validExceptionDTO = ValidExceptionDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errores(errores)
                .date(LocalDateTime.now())
                .build();
        return ResponseEntity
                .badRequest()
                .body(validExceptionDTO);
    }

    /**
     * Metodo que maneja la excepciones de parametros con anotaciones de validation
     * @param ex ConstraintViolationException
     * @return retorna un ValidExceptionDTO que contiene el mapa de Excepciones
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleConstraintViolation(ConstraintViolationException ex){
        Map<String, String> errores = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String campo = violation.getPropertyPath().toString();
            errores.put(campo, violation.getMessage());
        });
        ValidExceptionDTO validExceptionDTO = ValidExceptionDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errores(errores)
                .date(LocalDateTime.now())
                .build();
        return ResponseEntity
                .badRequest()
                .body(errores);
    }


    /**
     * Metodo que maneja la excepcion cuando no se encuentra una nota
     * @param ex NotaNotFoundException
     * @return retorna un ExceptionDTO
     */
    @ExceptionHandler(NotaNotFoundException.class)
    public ResponseEntity<ExceptionDTO> NotaNotFoundHandler(NotaNotFoundException ex){
        ExceptionDTO exceptionDTO = ExceptionDTO.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .date(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exceptionDTO);

    }

    /**
     * Metodo que maneja la exepcion que se genera al no encotrar el usuario
     * @param ex UsernameNotFoundException
     * @return retorna un exceptionDTO
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionDTO> UsernameNotFoundHandler(UsernameNotFoundException ex){
        ExceptionDTO exceptionDTO = ExceptionDTO.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .date(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exceptionDTO);
    }
}