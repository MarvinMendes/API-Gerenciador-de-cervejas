package com.digitalinnovation.cervejas.demo.exceptions;

public class CervejaJaCadastradaException extends Exception {
    public CervejaJaCadastradaException(String nome) {
        super(String.format("A cerveja de nome: %s já está cadastrada no banco de dados do sistema.", nome));
    }
}
