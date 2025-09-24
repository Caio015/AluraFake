package br.com.alura.AluraFake.task.adapter.in;

import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.port.in.CreateOpenTextTaskUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final CreateOpenTextTaskUseCase openTextExerciseUseCase;

    public TaskController(CreateOpenTextTaskUseCase openTextExerciseUseCase) {

        this.openTextExerciseUseCase = openTextExerciseUseCase;
    }

    @PostMapping("/task/new/opentext")
    public ResponseEntity<TaskResponseDTO> newOpenTextExercise(@RequestBody @Valid TaskDTO request) {

        Task task = openTextExerciseUseCase.execute(request.getCourseId(), request.getStatement(), request.getOrder());

         return ResponseEntity.status(HttpStatus.CREATED).body(TaskResponseDTO.of(task));
    }

    @PostMapping("/task/new/singlechoice")
    public ResponseEntity newSingleChoice() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/task/new/multiplechoice")
    public ResponseEntity newMultipleChoice() {
        return ResponseEntity.ok().build();
    }

}