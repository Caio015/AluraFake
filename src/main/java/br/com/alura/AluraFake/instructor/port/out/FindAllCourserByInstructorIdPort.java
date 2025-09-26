package br.com.alura.AluraFake.instructor.port.out;

import br.com.alura.AluraFake.course.domain.Course;

import java.util.List;

public interface FindAllCourserByInstructorIdPort {

   List<Course> findCourseByUserId(Long userId);

}
