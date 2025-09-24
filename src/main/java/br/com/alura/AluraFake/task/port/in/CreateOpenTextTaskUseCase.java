package br.com.alura.AluraFake.task.port.in;

import br.com.alura.AluraFake.task.domain.Task;

public interface CreateOpenTextTaskUseCase {

    Task execute(Long courseId, String statement, Integer order);

}
