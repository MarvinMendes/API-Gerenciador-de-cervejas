package com.digitalinnovation.cervejas.demo.exceptions;

public class CervejaNaoCadastradaException extends Exception{
    public CervejaNaoCadastradaException(Long id) {
        super(String.format("Cerveja com o id: %s não foi encontrada no sistema.", id));
    }

    public CervejaNaoCadastradaException(String nome) {
        super(String.format("Cerveja com o nome: %s não foi encontrada no sistema.", nome));
    }
}
