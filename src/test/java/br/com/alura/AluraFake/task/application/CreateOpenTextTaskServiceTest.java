package br.com.alura.AluraFake.task.application;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.out.FindCourseByIdPort;
import br.com.alura.AluraFake.course.ports.out.SaveCoursePort;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.Type;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateOpenTextTaskServiceTest {

    @Mock private  FindCourseByIdPort findCourseByIdPort = Mockito.mock(FindCourseByIdPort.class);
    @Mock private  SaveCoursePort saveCoursePort = Mockito.mock(SaveCoursePort.class);
    @InjectMocks private CreateOpenTextTaskService service;


    @Test
    void task__should__create__newOpenTextTask__andReturnIt() {

        User instructor = new User("Paulo", "paulo@aluta.com", Role.INSTRUCTOR);
        Course course = new Course("Java", "Primeiros Passos", instructor);

        when(findCourseByIdPort.findById(1L)).thenReturn(course);

        Task task = service.execute(1L, "Hello World!", 1);

        verify(saveCoursePort).save(course);

        assertThat(course.getTasks()).hasSize(1);
        assertThat(task.getStatement()).isEqualTo("Hello World!");
        assertThat(task.getOrder()).isEqualTo(1);
        assertThat(task.getType()).isEqualTo(Type.OPEN_TEXT);
    }
}



