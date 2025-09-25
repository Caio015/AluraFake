package br.com.alura.AluraFake.task.application;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.in.SaveCourseUseCase;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.TaskFactory;
import br.com.alura.AluraFake.task.domain.Type;
import br.com.alura.AluraFake.task.port.in.CreateOpenTextTaskUseCase;
import br.com.alura.AluraFake.course.ports.in.FindCourseByIdUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateOpenTextTaskService implements CreateOpenTextTaskUseCase {

    private final FindCourseByIdUseCase findCourseByIdPort;
    private final SaveCourseUseCase saveCoursePort;

    public CreateOpenTextTaskService(FindCourseByIdUseCase findCourseByIdPort, SaveCourseUseCase saveCoursePort) {

        this.findCourseByIdPort = findCourseByIdPort;
        this.saveCoursePort = saveCoursePort;
    }

    @Override
    public Task execute(Long courseId, String statement, Integer order) {

        Course course = findCourseByIdPort.findById(courseId);

        Task task = TaskFactory.createTask(course, statement, order, Type.OPEN_TEXT);

        course.addTask(task);

        saveCoursePort.save(course);

        return task;
    }
}
