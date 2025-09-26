package br.com.alura.AluraFake.course.adapter.in;

import br.com.alura.AluraFake.course.adapter.in.DTO.CourseListItemDTO;
import br.com.alura.AluraFake.course.adapter.in.DTO.NewCourseDTO;
import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.out.FindAllCoursesPort;
import br.com.alura.AluraFake.course.ports.in.PublishCourseUseCase;
import br.com.alura.AluraFake.course.ports.out.SaveCoursePort;
import br.com.alura.AluraFake.user.domain.User;
import br.com.alura.AluraFake.user.port.out.FindUserByEmailPort;
import br.com.alura.AluraFake.util.ErrorItemDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {

    private final SaveCoursePort saveCoursePort;
    private final FindUserByEmailPort userRepository;
    private final FindAllCoursesPort findAllCoursesUseCase;
    private final PublishCourseUseCase publishCourseUseCase;

    @Autowired
    public CourseController(SaveCoursePort saveCoursePort, FindUserByEmailPort userRepository,
                            FindAllCoursesPort findAllCoursesUseCase, PublishCourseUseCase publishCourseUseCase){

        this.saveCoursePort = saveCoursePort;
        this.userRepository = userRepository;
        this.findAllCoursesUseCase = findAllCoursesUseCase;
        this.publishCourseUseCase = publishCourseUseCase;
    }

    @Transactional
    @PostMapping("/course/new")
    public ResponseEntity createCourse(@Valid @RequestBody NewCourseDTO newCourse) {

        //Caso implemente o bonus, pegue o instrutor logado
        Optional<User> possibleAuthor = userRepository
                .findByEmail(newCourse.getEmailInstructor())
                .filter(User::isInstructor);

        if(possibleAuthor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorItemDTO("emailInstructor", "Usuário não é um instrutor"));
        }

        Course course = new Course(newCourse.getTitle(), newCourse.getDescription(), possibleAuthor.get());

        saveCoursePort.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/course/all")
    public ResponseEntity<List<CourseListItemDTO>> createCourse() {
        List<CourseListItemDTO> courses = findAllCoursesUseCase.findAll().stream()
                .map(CourseListItemDTO::new)
                .toList();
        return ResponseEntity.ok(courses);
    }

    @PostMapping("/course/{id}/publish")
    public ResponseEntity createCourse(@PathVariable("id") Long id) {

        publishCourseUseCase.publish(id);

        return ResponseEntity.noContent().build();
    }
}
