package br.com.alura.AluraFake.task.application;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.in.FindCourseByIdUseCase;
import br.com.alura.AluraFake.course.ports.in.SaveCourseUseCase;
import br.com.alura.AluraFake.task.adapter.in.DTO.TaskOptionDTO;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.Type;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateMultipleChoiceTaskServiceTest {

    @Mock private FindCourseByIdUseCase findCourseByIdPort;
    @Mock private SaveCourseUseCase saveCoursePort;
    @InjectMocks private CreateMultipleChoiceTaskService service;

    @Test
    void task__should__create_newMultipleChoiceTask__andReturnIt() {

        User instructor = new User("Paulo", "paulo@aluta.com", Role.INSTRUCTOR);
        Course course = new Course("Java", "Primeiros Passos", instructor);

        when(findCourseByIdPort.findById(1L)).thenReturn(course);

        List<TaskOptionDTO> options = List.of(new TaskOptionDTO("Java", true),
                                              new TaskOptionDTO("Python", false),
                                              new TaskOptionDTO("Ruby", true));

        Task task = service.execute(1L, "Qual alternativa correta?", 1, options);

        verify(saveCoursePort).save(course);

        assertEquals(1, course.getTasks().size());
        assertEquals("Qual alternativa correta?", task.getStatement());
        assertEquals(1, task.getOrder());
        assertEquals(Type.MULTIPLE_CHOICE, task.getType());
        assertEquals(3, task.getOptions().size());
        assertEquals("Java", task.getOptions().get(0).getOption());
        assertTrue(task.getOptions().get(0).isCorrect());
        assertEquals("Python", task.getOptions().get(1).getOption());
        assertFalse(task.getOptions().get(1).isCorrect());
        assertEquals("Ruby", task.getOptions().get(2).getOption());
        assertTrue(task.getOptions().get(2).isCorrect());
    }

}