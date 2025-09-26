package br.com.alura.AluraFake.exceptions;

public class UserMustBeAnInstructorException extends RuntimeException {

    public UserMustBeAnInstructorException() {

        super("User must be an instructor.");

    }

}
