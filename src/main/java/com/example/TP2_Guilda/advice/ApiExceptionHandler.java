package com.example.TP2_Guilda.advice;

import com.example.TP2_Guilda.DTO.ErrorResponseDTO;
import com.example.TP2_Guilda.exceptions.EntityNotFoundException;
import com.example.TP2_Guilda.exceptions.HeadersInvalidosException;
import com.example.TP2_Guilda.exceptions.RegrasDeNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlerAventureiroNaoLocalizado(EntityNotFoundException ex){

        ErrorResponseDTO erro = new ErrorResponseDTO("Recurso nao encontrado", List.of(ex.getMessage()));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(erro);

    }

    @ExceptionHandler(RegrasDeNegocioException.class)
    public ResponseEntity<ErrorResponseDTO> handlerRegrasDeNegocio(RegrasDeNegocioException ex){

        ErrorResponseDTO erro = new ErrorResponseDTO("Regra de negócio violada", List.of(ex.getMessage()));
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleJsonInvalido(HttpMessageNotReadableException ex) {

        String mensagem = "JSON inválido";

        ErrorResponseDTO erro = new ErrorResponseDTO(
                "Solicitação inválida",
                List.of(mensagem, "Verifique os dados enviado!")
        );

        return ResponseEntity
                .badRequest()
                .body(erro);
    }

//    TESTAR
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        String mensagem = String.format(
                "O parâmetro '%s' deve ser do tipo %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : null
        );

        ErrorResponseDTO erro = new ErrorResponseDTO(
                "Parâmetro inválido",
                List.of(mensagem)
        );

        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParam(MissingServletRequestParameterException ex) {

        String mensagem = String.format(
                "O parâmetro '%s' é obrigatório",
                ex.getParameterName()
        );

        ErrorResponseDTO erro = new ErrorResponseDTO(
                "Parâmetro obrigatório ausente",
                List.of(mensagem)
        );

        return ResponseEntity.badRequest().body(erro);
    }

}
