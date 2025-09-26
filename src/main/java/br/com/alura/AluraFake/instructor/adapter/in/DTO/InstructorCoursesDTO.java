package br.com.alura.AluraFake.instructor.adapter.in.DTO;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.domain.Status;

import java.util.List;

public class InstructorCoursesDTO {

    private final List<CourseDetailDTO> course;
    private final Long publishedCoursesAmount;

    public InstructorCoursesDTO(List<CourseDetailDTO> course, Long publishedCoursesAmount) {

        this.course = course;
        this.publishedCoursesAmount = publishedCoursesAmount;
    }

    public List<CourseDetailDTO> getCourse() {

        return course;
    }

    public Long getPublishedCoursesAmount() {

        return publishedCoursesAmount;
    }

    public static InstructorCoursesDTO of(List<Course> courses) {

        List<CourseDetailDTO> coursesDetails = mapToDto(courses);

        return new InstructorCoursesDTO(coursesDetails, countPublishedCoursesAmount(courses));

    }

    private static List<CourseDetailDTO> mapToDto(List<Course> courses) {

        return courses.stream()
                      .map(CourseDetailDTO::of)
                      .toList();
    }

    private static long countPublishedCoursesAmount(List<Course> courses) {

        return courses.stream()
                      .filter(course -> course.getStatus()
                                              .equals(Status.PUBLISHED))
                      .count();
    }
}
