package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.out.FindCourseByIdPort;
import br.com.alura.AluraFake.course.ports.in.PublishCourseUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PublishCourseService implements PublishCourseUseCase {

    private final FindCourseByIdPort findCourseByIdUseCase;

    public PublishCourseService(FindCourseByIdPort findCourseByIdUseCase) {

        this.findCourseByIdUseCase = findCourseByIdUseCase;
    }

    @Override
    public void publish(Long courseId) {

       Course course = findCourseByIdUseCase.findById(courseId);

       course.publishCourse();
    }
}
