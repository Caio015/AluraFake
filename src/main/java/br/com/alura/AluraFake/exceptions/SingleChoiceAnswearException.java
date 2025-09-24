package br.com.alura.AluraFake.exceptions;

public class SingleChoiceAnswearException extends RuntimeException {

    public SingleChoiceAnswearException() {

        super("Single choice tasks can only have one correct answear.");
    }
}
