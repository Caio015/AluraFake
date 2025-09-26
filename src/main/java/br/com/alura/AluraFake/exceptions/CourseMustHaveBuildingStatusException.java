package br.com.alura.AluraFake.exceptions;

public class CourseMustHaveBuildingStatusException extends DomainException {

    public CourseMustHaveBuildingStatusException() {
        super("Course status must be BUILDING to add tasks");
    }

}
