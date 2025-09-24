package br.com.alura.AluraFake.task.port.in;

import br.com.alura.AluraFake.task.adapter.in.DTO.TaskOptionDTO;
import br.com.alura.AluraFake.task.domain.Task;

import java.util.List;

public interface CreateSingleChoiceTaskUseCase {

    Task execute(Long courseId, String statement, Integer order, List<TaskOptionDTO> options);

}
