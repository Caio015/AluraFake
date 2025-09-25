package br.com.alura.AluraFake.task.application;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.out.FindCourseByIdPort;
import br.com.alura.AluraFake.course.ports.out.SaveCoursePort;
import br.com.alura.AluraFake.task.adapter.in.DTO.TaskOptionDTO;
import br.com.alura.AluraFake.task.domain.*;
import br.com.alura.AluraFake.task.port.in.CreateMultipleChoiceTaskUseCase;

import java.util.List;

public class CreateMultipleChoiceTaskService implements CreateMultipleChoiceTaskUseCase {

    private final FindCourseByIdPort findCourseByIdPort;
    private final SaveCoursePort saveCoursePort;

    public CreateMultipleChoiceTaskService(FindCourseByIdPort findCourseByIdPort, SaveCoursePort saveCoursePort) {

        this.findCourseByIdPort = findCourseByIdPort;
        this.saveCoursePort = saveCoursePort;
    }

    @Override
    public Task execute(Long courseId, String statement, Integer order, List<TaskOptionDTO> taskOptionsDTO) {

        Course course = findCourseByIdPort.findById(courseId);

        Task task = TaskFactory.createTask(course, statement, order, Type.MULTIPLE_CHOICE);

        List<TaskOption> taskOptions = TaskOptionMapper.from(taskOptionsDTO);

        task.addOptions(taskOptions);

        course.addTask(task);

        saveCoursePort.save(course);

        return task;
    }
}
