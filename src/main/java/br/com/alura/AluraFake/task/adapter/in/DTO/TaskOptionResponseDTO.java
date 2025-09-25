package br.com.alura.AluraFake.task.adapter.in.DTO;

import br.com.alura.AluraFake.task.domain.TaskOption;

public class TaskOptionResponseDTO {

    private final Long id;
    private final String option;
    private final boolean isCorrect;

    public TaskOptionResponseDTO(Long id, String option, boolean correct) {

        this.id = id;
        this.option = option;
        this.isCorrect = correct;
    }

    public Long getId() {

        return id;
    }

    public String getOption() {

        return option;
    }

    public boolean isCorrect() {

        return isCorrect;
    }

    public static TaskOptionResponseDTO of(TaskOption option) {

        return new TaskOptionResponseDTO(option.getId(), option.getOption(), option.isCorrect());
    }

}

