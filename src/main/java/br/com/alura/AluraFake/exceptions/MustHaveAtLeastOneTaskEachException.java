package br.com.alura.AluraFake.exceptions;

public class MustHaveAtLeastOneTaskEachException extends RuntimeException {

    public MustHaveAtLeastOneTaskEachException() {

        super("Course must have at least one task of each kind");
    }

}
