package br.com.alura.AluraFake.task.adapter.in.DTO;

import jakarta.validation.constraints.Size;

public class TaskOptionDTO {

    @Size(min = 4, max = 80, message = "The option must be between 4 and 80 characters")
    private final String option;
    private final Boolean isCorrect;

    public TaskOptionDTO(String option, Boolean isCorrect) {
        this.option = option;
        this.isCorrect = isCorrect;
    }

    public String getOption() {

        return option;
    }

    public Boolean getCorrect() {

        return isCorrect;
    }
}
