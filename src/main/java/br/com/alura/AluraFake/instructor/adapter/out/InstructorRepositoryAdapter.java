package br.com.alura.AluraFake.instructor.adapter.out;

import br.com.alura.AluraFake.course.adapter.out.CourseRepository;
import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.instructor.port.out.FindAllCourserByInstructorIdPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InstructorRepositoryAdapter implements FindAllCourserByInstructorIdPort {

    private final CourseRepository courseRepository;

    public InstructorRepositoryAdapter(CourseRepository courseRepository) {

        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findCourseByUserId(Long userId) {

        return courseRepository.findByInstructorId(userId);
    }
}
