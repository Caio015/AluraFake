package br.com.alura.AluraFake.task.adapter.in;

import br.com.alura.AluraFake.task.domain.TaskOption;

public class TaskOptionResponseDTO {

    private Long id;
    private String option;
    private boolean correct;

    public TaskOptionResponseDTO(Long id, String text, boolean correct) {
        this.id = id;
        this.option = text;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return option;
    }

    public boolean isCorrect() {
        return correct;
    }


    public static TaskOptionResponseDTO of(TaskOption option) {

        return new TaskOptionResponseDTO(
                option.getId(),
                option.getOption(),
                option.isCorrect()
        );
    }


}

