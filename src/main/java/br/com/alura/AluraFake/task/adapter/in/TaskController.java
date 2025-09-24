package br.com.alura.AluraFake.task.adapter.in;

import br.com.alura.AluraFake.task.port.in.NewOpenTextExerciseUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final NewOpenTextExerciseUseCase openTextExerciseUseCase;

    public TaskController(NewOpenTextExerciseUseCase openTextExerciseUseCase) {

        this.openTextExerciseUseCase = openTextExerciseUseCase;
    }

    @PostMapping("/task/new/opentext")
    public ResponseEntity newOpenTextExercise(@RequestBody @Valid TaskDTO request) {

        openTextExerciseUseCase.execute(request.getCourseId(), request.getStatement(), request.getOrder());

        return ResponseEntity.ok().build();
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