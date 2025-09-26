package br.com.alura.AluraFake.exceptions;

public class OptionMustBeDifferentFromStatementException extends DomainException {

    public OptionMustBeDifferentFromStatementException(String option) {

        super("Option '" + option + "' must be different from the statement.");
    }

}
