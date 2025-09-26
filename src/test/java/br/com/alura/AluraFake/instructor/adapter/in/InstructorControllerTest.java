package br.com.alura.AluraFake.instructor.adapter.in;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.exceptions.UserNotFoundException;
import br.com.alura.AluraFake.instructor.port.in.FindAllCourserByInstructorIdUseCase;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.TaskFactory;
import br.com.alura.AluraFake.task.domain.Type;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import br.com.alura.AluraFake.user.port.out.FindUserByIdPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstructorController.class)
class InstructorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindAllCourserByInstructorIdUseCase instructorCoursesUseCase;

    @MockBean
    private FindUserByIdPort findUserByIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listCourses__should_return_empty_list_when_no_courses() throws Exception {

        Long instructorId = 1L;

        when(instructorCoursesUseCase.findCourseByUserId(instructorId))
                .thenReturn(List.of());

        mockMvc.perform(get("/instructor/{id}/courses", instructorId)
                                              .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.course").isArray())
               .andExpect(jsonPath("$.course").isEmpty());
    }

    @Test
    void listCourses__should_return_courses_when_present() throws Exception {

        Long userId = 1L;

        User instructor = new User("Paulo", "paulo@alura.com", Role.INSTRUCTOR);


        Course course1 = new Course("Primeiro Curso", "Primeiro Curso", instructor);
        Course course2 = new Course("Segundo Curso", "Segundo Curso", instructor);

        Task task1 = TaskFactory.createTask(course2, "Qual a Pergunta?", 1, Type.OPEN_TEXT);
        Task task2 = TaskFactory.createTask(course2, "Escolha uma alternativa", 2, Type.SINGLE_CHOICE);
        Task task3 = TaskFactory.createTask(course2, "Escolha duas alternativas", 3, Type.MULTIPLE_CHOICE);

        course2.addTask(task1);
        course2.addTask(task2);
        course2.addTask(task3);

        course2.publishCourse();

        when(instructorCoursesUseCase.findCourseByUserId(userId))
                .thenReturn(List.of(course1, course2));

        mockMvc.perform(get("/instructor/{id}/courses", userId)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.course[0].title").value("Primeiro Curso"))
               .andExpect(jsonPath("$.course[0].status").value("BUILDING"))
               .andExpect(jsonPath("$.course[0].publishedAt").doesNotExist())
               .andExpect(jsonPath("$.course[0].taskAmount").value(0))
               .andExpect(jsonPath("$.course[1].title").value("Segundo Curso"))
               .andExpect(jsonPath("$.course[1].status").value("PUBLISHED"))
               .andExpect(jsonPath("$.course[1].publishedAt").isNotEmpty())
               .andExpect(jsonPath("$.course[1].taskAmount").value(3))
               .andExpect(jsonPath("$.publishedCoursesAmount").value(1));
    }

    @Test
    void getUserById_shouldReturn404_whenUserNotFound() throws Exception {

        Long userId = 1L;

        when(findUserByIdUseCase.findById(userId))
                .thenThrow(new UserNotFoundException(userId));

        mockMvc.perform(get("/users/{id}", userId)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }
}