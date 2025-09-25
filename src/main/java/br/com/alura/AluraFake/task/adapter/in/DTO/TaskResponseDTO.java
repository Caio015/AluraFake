package br.com.alura.AluraFake.task.adapter.in.DTO;

import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.Type;

import java.util.List;

public class TaskResponseDTO {

    private final Long id;
    private final String statement;
    private final Integer order;
    private final Type type;
    private final List<TaskOptionResponseDTO> options;

    public TaskResponseDTO(Long id, String statement, Integer order, Type type, List<TaskOptionResponseDTO> options) {

        this.id = id;
        this.statement = statement;
        this.order = order;
        this.type = type;
        this.options = options;
    }

    public Long getId() {

        return id;
    }

    public String getStatement() {

        return statement;
    }

    public Integer getOrder() {

        return order;
    }

    public Type getType() {

        return type;
    }

    public List<TaskOptionResponseDTO> getOptions() {

        return options;
    }

    public static TaskResponseDTO of(Task task) {

        List<TaskOptionResponseDTO> optionDtos = task.getOptions()
                                                     .stream()
                                                     .map(TaskOptionResponseDTO::of)
                                                     .toList();

        return new TaskResponseDTO(task.getId(), task.getStatement(), task.getOrder(), task.getType(), optionDtos);
    }
}
