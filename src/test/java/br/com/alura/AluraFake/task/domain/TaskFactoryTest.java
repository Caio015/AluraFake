package br.com.alura.AluraFake.task.domain;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.domain.Status;
import br.com.alura.AluraFake.exceptions.CourseMustHaveBuildingStatusException;
import br.com.alura.AluraFake.exceptions.DuplicateStatementException;
import br.com.alura.AluraFake.exceptions.InvalidOrderException;
import br.com.alura.AluraFake.exceptions.OrderCannotBeNegativeException;
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

    @Test
    void task__should__throw_duplicate_statement_exception__whenStatementAlreadyExists() {
        Task task = TaskFactory.createTask(course, "Aprender Java", 1, any());

        course.addTask(task);

        Task nextTask = TaskFactory.createTask(course, "Aprender Java", 2, any());

        assertThrows(DuplicateStatementException.class, () -> course.addTask(nextTask));
    }

    @Test
    void task__should__throw_order_cannot_be_negative_exception__whenOrderIsNegative() {

        Task task = TaskFactory.createTask(course, "Aprender Java", -1, any());

        assertThrows(OrderCannotBeNegativeException.class,
                     () -> course.addTask(task));
    }

    @Test
    void task__should__throw_invalid_order_exception__whenOrderIsOutOfSequence() {

        Task task = TaskFactory.createTask(course, "Aprender Java", 1, any());

        course.addTask(task);

        Task nextTask = TaskFactory.createTask(course, "Interfaces", 4, any());

        assertThrows(InvalidOrderException.class,
                     () -> course.addTask(nextTask));
    }

    @Test
    void task__should__throw_course_must_be_building_exception__whenCourseStatusNotBuilding() {

        Task task = TaskFactory.createTask(course, "Aprender Java", 1, any());

        course.setStatus(Status.PUBLISHED);

        assertThrows(CourseMustHaveBuildingStatusException.class,
                     () -> course.addTask(task));
    }
}


