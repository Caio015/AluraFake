package br.com.alura.AluraFake.course.adapter.out;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.user.adapter.out.UserRepository;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void findByInstructorId_should_return_courses_of_instructor() {

        User instructor = new User("Paulo", "paulo@alura.com", Role.INSTRUCTOR);

        User savedInstructor = userRepository.save(instructor);

        Course course = new Course("Primeiro Curso", "Primeiros passos", savedInstructor);

        courseRepository.save(course);

        assertThat(course.getTitle()).isEqualTo("Primeiro Curso");

        List<Course> courses = courseRepository.findByInstructorId(savedInstructor.getId());

        assertThat(courses.size()).isEqualTo(1);
        assertThat(courses.getFirst().getTitle()).isEqualTo("Primeiro Curso");
    }
}