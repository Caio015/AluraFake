package br.com.alura.AluraFake.task.adapter.in;

import jakarta.validation.constraints.Size;

import java.util.List;

public class TaskDTO {

    private Long courseId;
    @Size(min = 4, max = 255, message = "The statement must be between 4 and 255 characters")
    private String statement;
    private Integer order;
    private List<TaskOptionDTO> options;

    public TaskDTO(Long courseId, String statement, Integer order) {

        this.courseId = courseId;
        this.statement = statement;
        this.order = order;
    }

    public Long getCourseId() {

        return courseId;
    }

    public String getStatement() {

        return statement;
    }

    public Integer getOrder() {

        return order;
    }

    public List<TaskOptionDTO> getOptions() {

        return options;
    }
}
