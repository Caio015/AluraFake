package br.com.alura.AluraFake.course.ports.out;

import br.com.alura.AluraFake.course.domain.Course;

import java.util.List;

public interface FindAllCoursesPort {

    List<Course> findAll();
}
