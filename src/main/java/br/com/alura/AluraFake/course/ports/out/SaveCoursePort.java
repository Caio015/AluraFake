package br.com.alura.AluraFake.course.ports.out;

import br.com.alura.AluraFake.course.domain.Course;

public interface SaveCoursePort {

    Course save(Course course);

}
