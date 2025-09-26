package br.com.alura.AluraFake.instructor.application;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.exceptions.UserMustBeAnInstructorException;
import br.com.alura.AluraFake.exceptions.UserNotFoundException;
import br.com.alura.AluraFake.instructor.port.out.FindAllCourserByInstructorIdPort;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import br.com.alura.AluraFake.user.port.out.FindUserByIdPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllCourserByInstructorIdServiceTest {

    @Mock private FindAllCourserByInstructorIdPort findCourses;

    @Mock private FindUserByIdPort findUser;

    @InjectMocks private FindAllCourserByInstructorIdService service;

    @Test
    void findCourseByUserId_should_return_courses_when_user_is_instructor() {

        User instructor = new User("Paulo", "paulo@alura.com", Role.INSTRUCTOR);
        Course course1 = new Course("Primeiro Curso", "Primeiros Passos", instructor);
        Course course2 = new Course("Segundo Curso", "Proximos Passos", instructor);

        when(findUser.findById(1L)).thenReturn(instructor);
        when(findCourses.findCourseByUserId(1L)).thenReturn(List.of(course1, course2));

        List<Course> result = service.findCourseByUserId(1L);

        assertEquals(2, result.size());
        assertEquals("Primeiro Curso", result.get(0).getTitle());
        assertEquals("Primeiros Passos", result.get(0).getDescription());
        assertEquals(instructor, result.get(0).getInstructor());
        assertEquals("Segundo Curso", result.get(1).getTitle());
        assertEquals("Proximos Passos", result.get(1).getDescription());
        assertEquals(instructor, result.get(1).getInstructor());
    }

    @Test
    void findCourseByUserId_should_throw_exception_when_user_is_not_instructor() {

        User student = new User("Caio", "caio@alura.com", Role.STUDENT);

        when(findUser.findById(1L)).thenReturn(student);

        assertThrows(UserMustBeAnInstructorException.class, () -> service.findCourseByUserId(1L));
    }

    @Test
    void findCourseByUserId_should_throw_exception_when_user_not_found() {

        when(findUser.findById(1L)).thenThrow(new UserNotFoundException(1L));

        assertThrows(UserNotFoundException.class, () -> service.findCourseByUserId(1L));
    }
}

