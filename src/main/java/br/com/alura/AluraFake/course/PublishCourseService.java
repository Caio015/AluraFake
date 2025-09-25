package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.in.FindCourseByIdUseCase;
import br.com.alura.AluraFake.course.ports.in.PublishCourseUseCase;
import org.springframework.stereotype.Service;

@Service
public class PublishCourseService implements PublishCourseUseCase {

    private final FindCourseByIdUseCase findCourseByIdUseCase;

    public PublishCourseService(FindCourseByIdUseCase findCourseByIdUseCase) {

        this.findCourseByIdUseCase = findCourseByIdUseCase;
    }

    @Override
    public void publish(Long courseId) {

       Course course = findCourseByIdUseCase.findById(courseId);

       course.publishCourse();
    }
}
