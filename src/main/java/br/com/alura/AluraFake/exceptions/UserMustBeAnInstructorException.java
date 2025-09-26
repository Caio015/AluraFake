package br.com.alura.AluraFake.exceptions;

public class UserMustBeAnInstructorException extends DomainException {

    public UserMustBeAnInstructorException() {

        super("User must be an instructor.");

    }

}
