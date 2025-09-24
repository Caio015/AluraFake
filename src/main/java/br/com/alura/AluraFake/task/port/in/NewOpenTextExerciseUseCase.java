package br.com.alura.AluraFake.task.port.in;

import br.com.alura.AluraFake.task.adapter.in.TaskDTO;

public interface NewOpenTextExerciseUseCase {

    void execute(Long courseId, String statement, Integer order);

}
