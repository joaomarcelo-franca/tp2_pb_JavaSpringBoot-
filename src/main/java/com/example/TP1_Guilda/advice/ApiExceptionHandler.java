package com.example.TP1_Guilda.advice;

import com.example.TP1_Guilda.DTO.ErrorResponseDTO;
import com.example.TP1_Guilda.exceptions.EntityNotFoundException;
import com.example.TP1_Guilda.exceptions.HeadersInvalidosException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handlerAventureiroNaoLocalizado(EntityNotFoundException ex){

        ErrorResponseDTO erro = new ErrorResponseDTO("Recurso nao encontrado", List.of(ex.getMessage()));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(erro);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex) {

        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .toList();

        ErrorResponseDTO erro = new ErrorResponseDTO(
                "Solicitação inválida",
                erros
        );

        return ResponseEntity
                .badRequest()
                .body(erro);
    }

    @ExceptionHandler(HeadersInvalidosException.class)
    public ResponseEntity<ErrorResponseDTO> handleHeadersInvalidos(HeadersInvalidosException ex) {

        ErrorResponseDTO erro = new ErrorResponseDTO(
                "Solicitação inválida",
                ex.getErros()
        );

        return ResponseEntity
                .badRequest()
                .body(erro);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleJsonInvalido(HttpMessageNotReadableException ex) {

        String mensagem = "JSON inválido";

        if (ex.getMessage().contains("Classe")) {
            mensagem = "classe inválida";
        }

        ErrorResponseDTO erro = new ErrorResponseDTO(
                "Solicitação inválida",
                List.of(mensagem)
        );

        return ResponseEntity
                .badRequest()
                .body(erro);
    }

}
