package br.com.alura.AluraFake.task.domain;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.domain.Status;
import br.com.alura.AluraFake.exceptions.*;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;

@Component
public class TaskValidator {

    private TaskValidator() {
    }

    public static void validateDuplicateStatement(Course course, String statement) {


        if (course.getTasks()
                  .stream()
                  .map(Task::getStatement)
                  .anyMatch(s -> normalize(s).equals(normalize(statement)))) {

            throw new DuplicateStatementException(statement);
        }
    }

    public static void validateOrderNotNegative(Integer order) {

        if (order < 0) {

            throw new OrderCannotBeNegativeException();
        }
    }

    public static void validateCourseStatus(Course course) {

        if (!course.getStatus().equals(Status.BUILDING)) {

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

    public static void validateIfTaskHasCorrectAnswers(Type type, List<TaskOption> options) {

        long correctCount = options.stream().filter(TaskOption::isCorrect).count();
        long incorrectCount = options.stream().filter(t -> !t.isCorrect()).count();

        if (type.equals(Type.SINGLE_CHOICE) && correctCount != 1) {

            throw new CorrectAnswerNumberException("Single choice tasks must have exactly one correct answer.");
        }

        if (type.equals(Type.MULTIPLE_CHOICE) && (correctCount < 2 || incorrectCount < 1)) {

            throw new CorrectAnswerNumberException("Multiple choice tasks must have at least two correct answers and one incorrect answer.");
        }
    }

    public static void validateUniqueOptions(List<TaskOption> options) {

        if (options.stream()
                   .map(o -> normalize(o.getOption()))
                   .distinct()
                   .count() != options.size()) {

            throw new UniqueOptionsException();
        }
    }

    public static void validateOptionsMustNotBeEqualToStatement(String statement, List<TaskOption> options) {

        Optional<TaskOption> duplicateOption = options.stream()
                                                      .filter(o -> normalize(o.getOption()).equals(normalize(statement)))
                                                      .findFirst();

        duplicateOption.ifPresent(o -> {throw new OptionMustBeDifferentFromStatementException(o.getOption());

        });
    }

    public static void validateOptionsCountByTaskType(Type type, List<TaskOption> options) {

        int size = options.size();

        if (type == Type.SINGLE_CHOICE && (size < 2 || size > 5)) {

            throw new InvalidNumberOfOptionsException("Single choice tasks must have between 2 and 5 options");
        }

        if (type == Type.MULTIPLE_CHOICE && (size < 3 || size > 5)) {

            throw new InvalidNumberOfOptionsException("Multiple choice tasks must have between 3 and 5 options");
        }
    }

    private static String normalize(String text) {

        return Normalizer.normalize(text, Normalizer.Form.NFD)
                         .replaceAll("\\p{M}", "")
                         .toLowerCase()
                         .trim();
    }
}
