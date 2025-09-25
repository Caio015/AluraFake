package br.com.alura.AluraFake.course.domain;

import br.com.alura.AluraFake.exceptions.CourseMustHaveBuildingStatusException;
import br.com.alura.AluraFake.exceptions.MustHaveAtLeastOneTaskEachException;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.Type;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseValidator {

    private CourseValidator() {

    }

    public static void validateIfCourseHasAtLeastOneOfEachActivitie(Course course) {

        List<Task> tasks = course.getTasks();

        if (verifyIfTasksContainsType(tasks, Type.OPEN_TEXT) ||
            verifyIfTasksContainsType(tasks, Type.SINGLE_CHOICE) ||
            verifyIfTasksContainsType(tasks, Type.MULTIPLE_CHOICE)) {

            throw new MustHaveAtLeastOneTaskEachException();
        }
    }

    private static boolean verifyIfTasksContainsType(List<Task> tasks, Type type) {

        return tasks.stream()
                    .noneMatch(t -> t.getType().equals(type));
    }

    public static void validateCourseStatus(Course course) {

        if (!course.getStatus().equals(Status.BUILDING)) {

            throw new CourseMustHaveBuildingStatusException();
        }
    }

}
