package br.edu.faculdade.vagas.controller;

import org.springframework.data.core.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(PropertyReferenceException.class)
    public ProblemDetail ordenacaoInvalida(PropertyReferenceException e) {
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Não dá para ordenar por '" + e.getPropertyName() + "'."
        );
        problema.setTitle("Parâmetro inválido");
        return problema;
    }
}
