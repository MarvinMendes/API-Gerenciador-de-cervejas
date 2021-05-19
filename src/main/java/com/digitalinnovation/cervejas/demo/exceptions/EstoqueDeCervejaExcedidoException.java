package com.digitalinnovation.cervejas.demo.exceptions;

public class EstoqueDeCervejaExcedidoException extends Exception {
    public EstoqueDeCervejaExcedidoException(Long id, int quantidade) {
        super(String.format("Estoque de cerveja foi excedido cerveja: %s - quantidade%s ", id, quantidade));
    }
}
