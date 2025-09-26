package br.com.alura.AluraFake.instructor.adapter.in;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.instructor.adapter.in.DTO.InstructorCoursesDTO;
import br.com.alura.AluraFake.instructor.port.in.FindAllCourserByInstructorIdUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InstructorController {

    private final FindAllCourserByInstructorIdUseCase instructorCoursesUseCase;

    public InstructorController(FindAllCourserByInstructorIdUseCase instructorCoursesUseCase) {

        this.instructorCoursesUseCase = instructorCoursesUseCase;

    }

    @GetMapping("/instructor/{id}/courses")
    public ResponseEntity<InstructorCoursesDTO> listCourses(@PathVariable("id") Long id) {

        List<Course> courses = instructorCoursesUseCase.findCourseByUserId(id);

        return ResponseEntity.ok(InstructorCoursesDTO.of(courses));
    }
}
