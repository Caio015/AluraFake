package br.com.alura.AluraFake.exceptions;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException() {
        super("Invalid order: tasks must be added sequentially");
    }
}
