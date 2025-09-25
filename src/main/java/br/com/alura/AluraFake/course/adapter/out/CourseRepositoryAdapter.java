package br.com.alura.AluraFake.course.adapter.out;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.in.FindAllCoursesUseCase;
import br.com.alura.AluraFake.course.ports.in.FindCourseByIdUseCase;
import br.com.alura.AluraFake.course.ports.in.SaveCourseUseCase;
import br.com.alura.AluraFake.exceptions.ItemNotFoundException;

import java.util.List;

public class CourseRepositoryAdapter implements FindCourseByIdUseCase,
                                                SaveCourseUseCase,
                                                FindAllCoursesUseCase {

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

    @Override
    public List<Course> findAll() {

        return repository.findAll();
    }
}
