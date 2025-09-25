package br.com.alura.AluraFake.course.ports.in;

import br.com.alura.AluraFake.course.domain.Course;

public interface FindCourseByIdUseCase {

    Course findById(Long courseId);

}
