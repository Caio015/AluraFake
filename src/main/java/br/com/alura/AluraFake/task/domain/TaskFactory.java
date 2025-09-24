package br.com.alura.AluraFake.task.domain;

import br.com.alura.AluraFake.course.domain.Course;

public class TaskFactory {

    private TaskFactory() {

    }

    public static Task createTask(Course course, String statement, Integer order) {

        return new Task(course, statement, order);

    }
}
