package br.com.alura.AluraFake.exceptions;

public class UniqueOptionsException extends RuntimeException {

    public UniqueOptionsException() {

        super("Option must be unique.");
    }

}
