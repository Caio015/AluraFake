package br.com.alura.AluraFake.exceptions;

public class OrderCannotBeNegativeException extends DomainException {

    public OrderCannotBeNegativeException() {
        super("Order cannot be negative");
    }

}
