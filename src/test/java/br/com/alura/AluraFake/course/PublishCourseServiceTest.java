package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.domain.Status;
import br.com.alura.AluraFake.course.ports.out.FindCourseByIdPort;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.TaskFactory;
import br.com.alura.AluraFake.task.domain.Type;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublishCourseServiceTest {

    @Mock private FindCourseByIdPort findCourseByIdUseCase;
    @InjectMocks private PublishCourseService service;

    @Test
    void publish_should_call_publishCourse_on_course() {

        User instructor = new User("Paulo", "paulo@alura.com", Role.INSTRUCTOR);
        Course course = new Course("Java", "Primeiros Passos", instructor);

        when(findCourseByIdUseCase.findById(1L)).thenReturn(course);

        Task task1 = TaskFactory.createTask(course, "Qual a Pergunta?", 1, Type.OPEN_TEXT);
        Task task2 = TaskFactory.createTask(course, "Escolha uma alternativa", 2, Type.SINGLE_CHOICE);
        Task task3 = TaskFactory.createTask(course, "Escolha duas alternativas", 3, Type.MULTIPLE_CHOICE);

        course.addTask(task1);
        course.addTask(task2);
        course.addTask(task3);

        service.publish(1L);

        assertNotNull(course.getPublishedAt());
        assertEquals(Status.PUBLISHED, course.getStatus());

    }
}
