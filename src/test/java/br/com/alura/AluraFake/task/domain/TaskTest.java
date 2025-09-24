package br.com.alura.AluraFake.task.domain;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.exceptions.InvalidNumberOfOptionsException;
import br.com.alura.AluraFake.exceptions.OptionMustBeDifferentFromStatementException;
import br.com.alura.AluraFake.exceptions.SingleChoiceAnswearException;
import br.com.alura.AluraFake.exceptions.UniqueOptionsException;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    User instructor = new User("Paulo", "paulo@aluta.com", Role.INSTRUCTOR);
    Course course = new Course("Java", "Primeiros Passos", instructor);
    Task task = new Task(course, "Qual linguagem desse curso?", 1, Type.SINGLE_CHOICE);

    @Test
    void addOptions__should_add_options_when_all_valid() {

        List<TaskOption> options = List.of(new TaskOption("Java", true), new TaskOption("Python", false));

        task.addOptions(options);

        assertEquals(2,
                     task.getOptions()
                         .size());
    }

    @Test
    void validateOptionsCountByTaskType__should_throw_exception_when_single_choice_has_less_than_2_options() {

        List<TaskOption> options = List.of(new TaskOption("Java", true));

        assertThrows(InvalidNumberOfOptionsException.class,
                     () -> TaskValidator.validateOptionsCountByTaskType(Type.SINGLE_CHOICE, options));
    }

    @Test
    void validateOptionsCountByTaskType__should_throw_exception_when_single_choice_has_more_than_5_options() {

        List<TaskOption> options = List.of(new TaskOption("Java", false),
                                           new TaskOption("Python", false),
                                           new TaskOption("C#", false),
                                           new TaskOption("Ruby", false),
                                           new TaskOption("Golang", false),
                                           new TaskOption("Javascript", false));

        assertThrows(InvalidNumberOfOptionsException.class,
                     () -> TaskValidator.validateOptionsCountByTaskType(Type.MULTIPLE_CHOICE, options));
    }

    @Test
    void addOptions__should_throw_single_choice_answer_exception_when_multiple_correct_options() {

        List<TaskOption> options = List.of(new TaskOption("Java", true),
                                           new TaskOption("Python", true));

        assertThrows(SingleChoiceAnswearException.class, () -> task.addOptions(options));
    }

    @Test
    void addOptions__should_throw_unique_options_exception_when_duplicate_options() {

        List<TaskOption> options = List.of(new TaskOption("Java", true), new TaskOption("javá", false));

        assertThrows(UniqueOptionsException.class, () -> task.addOptions(options));
    }

    @Test
    void addOptions__should_throw_option_must_be_different_from_statement_exception_when_option_equals_statement() {

        List<TaskOption> options = List.of(new TaskOption("Java", true),
                                           new TaskOption("QUal Linguagem DESSE cursó?", false));

        assertThrows(OptionMustBeDifferentFromStatementException.class, () -> task.addOptions(options));
    }

}