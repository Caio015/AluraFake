package br.com.alura.AluraFake.task.application;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.in.FindCourseByIdUseCase;
import br.com.alura.AluraFake.course.ports.in.SaveCourseUseCase;
import br.com.alura.AluraFake.task.adapter.in.DTO.TaskOptionDTO;
import br.com.alura.AluraFake.task.domain.*;
import br.com.alura.AluraFake.task.port.in.CreateSingleChoiceTaskUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CreateSingleChoiceTaskService implements CreateSingleChoiceTaskUseCase {

    private final FindCourseByIdUseCase findCourseByIdPort;
    private final SaveCourseUseCase saveCoursePort;

    public CreateSingleChoiceTaskService(FindCourseByIdUseCase findCourseByIdPort, SaveCourseUseCase saveCoursePort) {

        this.findCourseByIdPort = findCourseByIdPort;
        this.saveCoursePort = saveCoursePort;
    }

    @Override
    public Task execute(Long courseId, String statement, Integer order, List<TaskOptionDTO> taskOptionsDTO) {

        Course course = findCourseByIdPort.findById(courseId);

        Task task = TaskFactory.createTask(course, statement, order, Type.SINGLE_CHOICE);

        List<TaskOption> taskOptions = TaskOptionMapper.from(taskOptionsDTO);

        task.addOptions(taskOptions);

        course.addTask(task);

        saveCoursePort.save(course);

        return task;
    }
}
