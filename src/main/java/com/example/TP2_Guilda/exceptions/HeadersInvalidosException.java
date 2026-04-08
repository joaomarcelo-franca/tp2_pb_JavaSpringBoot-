package com.example.TP2_Guilda.exceptions;

import java.util.List;

public class HeadersInvalidosException extends RuntimeException {

    private final List<String> erros;

    public HeadersInvalidosException(List<String> erros) {
        this.erros = erros;
    }

    public List<String> getErros() {
        return erros;
    }
}
