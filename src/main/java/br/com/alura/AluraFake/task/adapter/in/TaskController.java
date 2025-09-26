package br.com.alura.AluraFake.task.adapter.in;

import br.com.alura.AluraFake.task.adapter.in.DTO.TaskDTO;
import br.com.alura.AluraFake.task.adapter.in.DTO.TaskResponseDTO;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.port.in.CreateMultipleChoiceTaskUseCase;
import br.com.alura.AluraFake.task.port.in.CreateOpenTextTaskUseCase;
import br.com.alura.AluraFake.task.port.in.CreateSingleChoiceTaskUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final CreateOpenTextTaskUseCase openTextExerciseUseCase;
    private final CreateSingleChoiceTaskUseCase singleChoiceUseCase;
    private final CreateMultipleChoiceTaskUseCase multipleChoiceTaskUseCase;

    public TaskController(CreateOpenTextTaskUseCase openTextExerciseUseCase,
                          CreateSingleChoiceTaskUseCase singleChoiceUseCase,
                          CreateMultipleChoiceTaskUseCase multipleChoiceTaskUseCase) {

        this.openTextExerciseUseCase = openTextExerciseUseCase;
        this.singleChoiceUseCase = singleChoiceUseCase;
        this.multipleChoiceTaskUseCase = multipleChoiceTaskUseCase;
    }

    @PostMapping("/task/new/opentext")
    public ResponseEntity<TaskResponseDTO> newOpenTextExercise(@RequestBody @Valid TaskDTO request) {

        Task task = openTextExerciseUseCase.execute(request.getCourseId(), request.getStatement(), request.getOrder());

        return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponseDTO.of(task));
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity<TaskResponseDTO> newSingleChoice(@RequestBody @Valid TaskDTO request) {

        Task task = singleChoiceUseCase.execute(request.getCourseId(), request.getStatement(), request.getOrder(), request.getOptions());

        return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponseDTO.of(task));
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity<TaskResponseDTO> newMultipleChoice(@RequestBody @Valid TaskDTO request) {

        Task task = multipleChoiceTaskUseCase.execute(request.getCourseId(), request.getStatement(), request.getOrder(), request.getOptions());

        return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponseDTO.of(task));
    }
}