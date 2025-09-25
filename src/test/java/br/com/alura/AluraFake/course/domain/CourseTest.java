package br.com.alura.AluraFake.course.domain;

import br.com.alura.AluraFake.exceptions.CourseMustHaveBuildingStatusException;
import br.com.alura.AluraFake.exceptions.DuplicateStatementException;
import br.com.alura.AluraFake.exceptions.InvalidOrderException;
import br.com.alura.AluraFake.exceptions.OrderCannotBeNegativeException;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.TaskFactory;
import br.com.alura.AluraFake.task.domain.Type;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTaskTest {

    private final User instructor = new User("Paulo", "paulo@aluta.com", Role.INSTRUCTOR);
    private final Course course = new Course("Java", "Primeiros Passos", instructor);

    @Test
    void addTask__should_add_task_when_valid() {

        course.setStatus(Status.BUILDING);

        Task task = TaskFactory.createTask(course, "O que você aprendeu?", 1, Type.OPEN_TEXT);

        course.addTask(task);

        assertEquals(1, course.getTasks().size());
        assertEquals("O que você aprendeu?", course.getTasks().get(0).getStatement());
    }

    @Test
    void addTask__should_throw_exception_when_course_is_not_building() {

        course.setStatus(Status.PUBLISHED);

        Task task = TaskFactory.createTask(course, "Qual linguagem desse curso?", 1, Type.SINGLE_CHOICE);

        assertThrows(CourseMustHaveBuildingStatusException.class,
                     () -> course.addTask(task));
    }

    @Test
    void addTask__should_throw_exception_when_statement_is_duplicated() {

        course.setStatus(Status.BUILDING);

        Task first = TaskFactory.createTask(course, "Qual linguagem desse curso?", 1, Type.SINGLE_CHOICE);
        course.addTask(first);

        Task duplicate = TaskFactory.createTask(course, "Qual linguagem desse curso?", 2, Type.SINGLE_CHOICE);

        assertThrows(DuplicateStatementException.class,
                     () -> course.addTask(duplicate));
    }

    @Test
    void addTask__should_throw_exception_when_order_is_negative() {

        course.setStatus(Status.BUILDING);

        Task task = TaskFactory.createTask(course, "Qual linguagem desse curso?", -1, Type.SINGLE_CHOICE);

        assertThrows(OrderCannotBeNegativeException.class,
                     () -> course.addTask(task));
    }

    @Test
    void addTask__should_throw_exception_when_order_is_not_sequential() {

        course.setStatus(Status.BUILDING);

        course.addTask(TaskFactory.createTask(course, "Pergunta 1", 1, Type.SINGLE_CHOICE));
        course.addTask(TaskFactory.createTask(course, "Pergunta 2", 2, Type.SINGLE_CHOICE));

        Task task = TaskFactory.createTask(course, "Pergunta 10", 10, Type.SINGLE_CHOICE);

        assertThrows(InvalidOrderException.class,
                     () -> course.addTask(task));
    }

    @Test
    void addTask__should_shift_orders_when_same_order_exists() {

        course.setStatus(Status.BUILDING);

        Task first = TaskFactory.createTask(course, "Pergunta 1", 1, Type.SINGLE_CHOICE);
        Task second = TaskFactory.createTask(course, "Pergunta 2", 2, Type.SINGLE_CHOICE);
        course.addTask(first);
        course.addTask(second);

        Task newTask = TaskFactory.createTask(course, "Pergunta nova", 1, Type.SINGLE_CHOICE);
        course.addTask(newTask);

        assertEquals(1, newTask.getOrder());
        assertEquals(2, first.getOrder());
        assertEquals(3, second.getOrder());
    }
}