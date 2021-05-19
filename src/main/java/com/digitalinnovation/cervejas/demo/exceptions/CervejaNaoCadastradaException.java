package com.digitalinnovation.cervejas.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CervejaNaoCadastradaException extends Exception{
    public CervejaNaoCadastradaException(Long id) {
        super(String.format("Cerveja com o id: %s não foi encontrada no sistema.", id));
    }

    public CervejaNaoCadastradaException(String nome) {
        super(String.format("Cerveja com o nome: %s não foi encontrada no sistema.", nome));
    }
}
