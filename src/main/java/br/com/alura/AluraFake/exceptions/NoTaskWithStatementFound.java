package br.com.alura.AluraFake.exceptions;

public class NoTaskWithStatementFound extends DomainException {

    public NoTaskWithStatementFound(String statement) {

        super("No task with " + statement + " found.");
    }

}
