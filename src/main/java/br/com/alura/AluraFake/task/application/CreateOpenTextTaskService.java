package br.com.alura.AluraFake.task.application;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.out.SaveCoursePort;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.TaskFactory;
import br.com.alura.AluraFake.task.port.in.CreateOpenTextTaskUseCase;
import br.com.alura.AluraFake.course.ports.out.FindCourseByIdPort;
import org.springframework.stereotype.Service;

@Service
public class CreateOpenTextTaskService implements CreateOpenTextTaskUseCase {

    private final FindCourseByIdPort findCourseByIdPort;
    private final SaveCoursePort saveCoursePort;

    public CreateOpenTextTaskService(FindCourseByIdPort findCourseByIdPort, SaveCoursePort saveCoursePort) {

        this.findCourseByIdPort = findCourseByIdPort;
        this.saveCoursePort = saveCoursePort;
    }

    @Override
    public Task execute(Long courseId, String statement, Integer order) {

        Course course = findCourseByIdPort.findById(courseId);

        Task task = TaskFactory.createTask(course, statement, order);

        course.addTask(task);

        saveCoursePort.save(course);

        return task;
    }
}
