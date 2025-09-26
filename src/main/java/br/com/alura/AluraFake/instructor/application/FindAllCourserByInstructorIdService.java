package br.com.alura.AluraFake.instructor.application;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.exceptions.UserMustBeAnInstructorException;
import br.com.alura.AluraFake.instructor.port.in.FindAllCourserByInstructorIdUseCase;
import br.com.alura.AluraFake.instructor.port.out.FindAllCourserByInstructorIdPort;
import br.com.alura.AluraFake.user.domain.User;
import br.com.alura.AluraFake.user.port.out.FindUserByIdPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllCourserByInstructorIdService implements FindAllCourserByInstructorIdUseCase {

    private final FindAllCourserByInstructorIdPort findCourses;
    private final FindUserByIdPort findUser;

    public FindAllCourserByInstructorIdService(FindAllCourserByInstructorIdPort findCourses, FindUserByIdPort findUser) {

        this.findCourses = findCourses;
        this.findUser = findUser;
    }

    @Override
    public List<Course> findCourseByUserId(Long userId) {

        User user = findUser.findById(userId);

        validateIfUserIsAnInstructor(user);

        return findCourses.findCourseByUserId(userId);
    }

    private static void validateIfUserIsAnInstructor(User user) {

        if(!user.isInstructor()) {

            throw new UserMustBeAnInstructorException();
        }
    }
}
