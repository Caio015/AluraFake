package br.com.alura.AluraFake.instructor.port.in;

import br.com.alura.AluraFake.course.domain.Course;

import java.util.List;

public interface FindAllCourserByInstructorIdUseCase {

    List<Course> findCourseByUserId(Long userId);

}
