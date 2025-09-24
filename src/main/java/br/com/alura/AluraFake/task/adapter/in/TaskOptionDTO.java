package br.com.alura.AluraFake.task.adapter.in;

public class TaskOptionDTO {

    private String option;
    private Boolean isCorrect;

    public TaskOptionDTO(String option, Boolean isCorrect) {
        this.option = option;
        this.isCorrect = isCorrect;
    }
}
