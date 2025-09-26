package br.com.alura.AluraFake.exceptions;

public class UserNotFoundException extends DomainException {

    public UserNotFoundException(Long id) {

        super("User " + id + " not found.");
    }
}