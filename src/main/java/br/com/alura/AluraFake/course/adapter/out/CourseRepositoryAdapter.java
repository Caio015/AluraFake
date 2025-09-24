package br.com.alura.AluraFake.course.adapter.out;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.out.FindCourseByIdPort;
import br.com.alura.AluraFake.course.ports.out.SaveCoursePort;
import br.com.alura.AluraFake.exceptions.ItemNotFoundException;

public class CourseRepositoryAdapter implements FindCourseByIdPort,
                                                SaveCoursePort {

    private final CourseRepository repository;

    public CourseRepositoryAdapter(CourseRepository repository) {

        this.repository = repository;
    }

    @Override
    public Course findById(Long courseId) {

        return repository.findById(courseId).orElseThrow(() -> new ItemNotFoundException(courseId));
    }

    @Override
    public void save(Course course) {

        repository.save(course);
    }
}
