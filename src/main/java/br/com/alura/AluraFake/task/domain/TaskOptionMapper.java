package br.com.alura.AluraFake.task.domain;

import br.com.alura.AluraFake.task.adapter.in.DTO.TaskOptionDTO;

import java.util.List;

public class TaskOptionMapper {

    protected TaskOptionMapper() {

    }

    public static List<TaskOption> from(Task task, List<TaskOptionDTO> options) {

        return options.stream()
                      .map(dto -> new TaskOption(dto.getOption(), dto.getCorrect(), task))
                      .toList();
    }
}
