package br.com.alura.AluraFake.task.domain;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.domain.Status;
import br.com.alura.AluraFake.exceptions.CourseMustHaveBuildingStatusException;
import br.com.alura.AluraFake.exceptions.DuplicateStatementException;
import br.com.alura.AluraFake.exceptions.OrderCannotBeNegativeException;


public class TaskFactory {

    private TaskFactory() {
    }

    public static Task createTask(Course course, String statement, Integer order) {

        validateDuplicateStatement(course, statement);
        validateOrderNotNegative(order);
        validateCourseStatus(course);

        return new Task(course, statement, order);

    }

    public static void validateDuplicateStatement(Course course, String statement) {

        if(course.getTasks().stream().map(Task::getStatement).anyMatch(s -> s.equals(statement))) {

            throw new DuplicateStatementException(statement);
        }
    }

    public static void validateOrderNotNegative(Integer order) {

        if(order < 0) {

            throw new OrderCannotBeNegativeException();
        }
    }

    public static void validateCourseStatus(Course course) {

        if(!course.getStatus().equals(Status.BUILDING)) {

            throw new CourseMustHaveBuildingStatusException();
        }
    }
}
