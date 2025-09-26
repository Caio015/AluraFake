package br.com.alura.AluraFake.exceptions;

public class UniqueOptionsException extends DomainException {

    public UniqueOptionsException() {

        super("Option must be unique.");
    }

}
