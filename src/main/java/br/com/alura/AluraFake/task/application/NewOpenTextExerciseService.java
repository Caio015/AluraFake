package br.com.alura.AluraFake.task.application;

import br.com.alura.AluraFake.course.adapter.out.CourseRepository;
import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.exceptions.DuplicateStatementException;
import br.com.alura.AluraFake.exceptions.ItemNotFoundException;
import br.com.alura.AluraFake.task.adapter.in.TaskDTO;
import br.com.alura.AluraFake.task.adapter.out.TaskRepository;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.TaskFactory;
import br.com.alura.AluraFake.task.port.in.NewOpenTextExerciseUseCase;
import org.springframework.stereotype.Service;

@Service
public class NewOpenTextExerciseService implements NewOpenTextExerciseUseCase {

    private final TaskRepository taskRepository;
    private final CourseRepository courseRepository;

    public NewOpenTextExerciseService(TaskRepository taskRepository, CourseRepository courseRepository) {

        this.taskRepository = taskRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void execute(Long courseId, String statement, Integer order) {

        Course course = courseRepository.findById(courseId)
                                        .orElseThrow(() -> new ItemNotFoundException(courseId));

        Task task = TaskFactory.createTask(course, statement, order);

        taskRepository.save(task);

    }
}
