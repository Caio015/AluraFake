package br.com.alura.AluraFake.exceptions;

public class DuplicateStatementException extends RuntimeException {

    public DuplicateStatementException(String statement) {
        super("Task statement already exists: " + statement);
    }

}
