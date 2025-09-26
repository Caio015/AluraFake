package br.com.alura.AluraFake.instructor.adapter.in.DTO;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.domain.Status;

import java.time.LocalDateTime;

public class CourseDetailDTO {

    private final Long courseId;
    private final String title;
    private final Status status;
    private final LocalDateTime publishedAt;
    private final Integer taskAmount;

    public CourseDetailDTO(Long courseId, String title, Status status, LocalDateTime publishedAt, Integer taskAmount) {

        this.courseId = courseId;
        this.title = title;
        this.status = status;
        this.publishedAt = publishedAt;
        this.taskAmount = taskAmount;
    }

    public Long getCourseId() {

        return courseId;
    }

    public String getTitle() {

        return title;
    }

    public Status getStatus() {

        return status;
    }

    public LocalDateTime getPublishedAt() {

        return publishedAt;
    }

    public Integer getTaskAmount() {

        return taskAmount;
    }

    public static CourseDetailDTO of(Course course) {

        return new CourseDetailDTO(
                course.getId(),
                course.getTitle(),
                course.getStatus(),
                course.getPublishedAt(),
                course.getTasks().size()
        );
    }


}
