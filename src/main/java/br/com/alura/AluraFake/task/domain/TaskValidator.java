package br.com.alura.AluraFake.task.domain;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.domain.Status;
import br.com.alura.AluraFake.exceptions.CourseMustHaveBuildingStatusException;
import br.com.alura.AluraFake.exceptions.DuplicateStatementException;
import br.com.alura.AluraFake.exceptions.InvalidOrderException;
import br.com.alura.AluraFake.exceptions.OrderCannotBeNegativeException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskValidator {

    public static void validateDuplicateStatement(Course course, String statement) {

        if (course.getTasks()
                  .stream()
                  .map(Task::getStatement)
                  .anyMatch(s -> s.equals(statement))) {

            throw new DuplicateStatementException(statement);
        }
    }

    public static void validateOrderNotNegative(Integer order) {

        if (order < 0) {

            throw new OrderCannotBeNegativeException();
        }
    }

    public static void validateCourseStatus(Course course) {

        if (!course.getStatus()
                   .equals(Status.BUILDING)) {

            throw new CourseMustHaveBuildingStatusException();
        }
    }

    public static void validateSequentialOrder(List<Integer> orderList, Integer order) {

        Integer maxOrder = orderList.stream()
                                    .max(Integer::compareTo)
                                    .orElse(0);

        if (order > maxOrder + 1) {

            throw new InvalidOrderException();
        }
    }
}
