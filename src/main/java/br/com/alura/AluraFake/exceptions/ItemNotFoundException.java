package br.com.alura.AluraFake.exceptions;

public class ItemNotFoundException extends DomainException {

    public ItemNotFoundException(Long id) {
        super("Item not found with id " + id);
    }

}
