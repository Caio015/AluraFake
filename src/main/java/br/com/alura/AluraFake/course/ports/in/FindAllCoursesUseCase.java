package br.com.alura.AluraFake.course.ports.in;

import br.com.alura.AluraFake.course.domain.Course;

import java.util.List;

public interface FindAllCoursesUseCase {

    List<Course> findAll();
}
