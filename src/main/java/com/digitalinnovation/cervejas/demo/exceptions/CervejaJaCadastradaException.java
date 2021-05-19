package com.digitalinnovation.cervejas.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CervejaJaCadastradaException extends Exception {
    public CervejaJaCadastradaException(String nome) {
        super(String.format("A cerveja de nome: %s já está cadastrada no banco de dados do sistema.", nome));
    }
}
