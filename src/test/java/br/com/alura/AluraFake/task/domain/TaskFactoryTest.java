package br.com.alura.AluraFake.task.domain;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class TaskFactoryTest {

    private final User instructor = new User("Paulo", "paulo@alura.com", Role.INSTRUCTOR);
    private final Course course = new Course("Java", "Primeiros Passos", instructor);

    @Test
    void task__should__create__successfully__whenDataIsValid() {
        Task task = TaskFactory.createTask(course, "Aprender Java", 1, any());

        assertEquals("Aprender Java", task.getStatement());
        assertEquals(1, task.getOrder());
        assertEquals(course, task.getCourse());
    }
}


