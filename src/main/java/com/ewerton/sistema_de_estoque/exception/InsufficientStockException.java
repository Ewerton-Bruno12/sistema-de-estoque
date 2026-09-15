package com.ewerton.sistema_de_estoque.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String message) { super(message);}
}
