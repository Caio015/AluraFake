package br.com.alura.AluraFake.exceptions;

public class OrderCannotBeNegativeException extends RuntimeException {

    public OrderCannotBeNegativeException() {
        super("Order cannot be negative");
    }

}
