package br.com.alura.AluraFake.course;

import br.com.alura.AluraFake.course.adapter.in.CourseController;
import br.com.alura.AluraFake.course.adapter.in.NewCourseDTO;
import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.course.ports.in.FindAllCoursesUseCase;
import br.com.alura.AluraFake.course.ports.in.PublishCourseUseCase;
import br.com.alura.AluraFake.course.ports.in.SaveCourseUseCase;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import br.com.alura.AluraFake.user.port.in.FindUserByEmailUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaveCourseUseCase saveCourseUseCase;

    @MockBean
    private FindUserByEmailUseCase findUserByEmailUseCase;

    @MockBean
    private FindAllCoursesUseCase findAllCoursesUseCase;

    @MockBean
    private PublishCourseUseCase publishCourseUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void newCourseDTO__should_return_bad_request_when_email_is_invalid() throws Exception {
        NewCourseDTO newCourseDTO = new NewCourseDTO();
        newCourseDTO.setTitle("Java");
        newCourseDTO.setDescription("Curso de Java");
        newCourseDTO.setEmailInstructor("paulo@alura.com.br");

        when(findUserByEmailUseCase.findByEmail(newCourseDTO.getEmailInstructor()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/course/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newCourseDTO)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.field").value("emailInstructor"))
               .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void newCourseDTO__should_return_bad_request_when_email_is_no_instructor() throws Exception {
        NewCourseDTO newCourseDTO = new NewCourseDTO();
        newCourseDTO.setTitle("Java");
        newCourseDTO.setDescription("Curso de Java");
        newCourseDTO.setEmailInstructor("paulo@alura.com.br");

        User user = mock(User.class);
        when(user.isInstructor()).thenReturn(false);
        when(findUserByEmailUseCase.findByEmail(newCourseDTO.getEmailInstructor()))
                .thenReturn(Optional.of(user));

        mockMvc.perform(post("/course/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newCourseDTO)))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.field").value("emailInstructor"))
               .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void newCourseDTO__should_return_created_when_new_course_request_is_valid() throws Exception {
        NewCourseDTO newCourseDTO = new NewCourseDTO();
        newCourseDTO.setTitle("Java");
        newCourseDTO.setDescription("Curso de Java");
        newCourseDTO.setEmailInstructor("paulo@alura.com.br");

        User user = mock(User.class);
        when(user.isInstructor()).thenReturn(true);
        when(findUserByEmailUseCase.findByEmail(newCourseDTO.getEmailInstructor()))
                .thenReturn(Optional.of(user));

        mockMvc.perform(post("/course/new")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newCourseDTO)))
               .andExpect(status().isCreated());

        verify(saveCourseUseCase, times(1)).save(any(Course.class));
    }

    @Test
    void listAllCourses__should_list_all_courses() throws Exception {
        User paulo = new User("Paulo", "paulo@alua.com.br", Role.INSTRUCTOR);
        Course java = new Course("Java", "Curso de java", paulo);
        Course hibernate = new Course("Hibernate", "Curso de hibernate", paulo);
        Course spring = new Course("Spring", "Curso de spring", paulo);

        when(findAllCoursesUseCase.findAll()).thenReturn(Arrays.asList(java, hibernate, spring));

        mockMvc.perform(get("/course/all")
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].title").value("Java"))
               .andExpect(jsonPath("$[0].description").value("Curso de java"))
               .andExpect(jsonPath("$[1].title").value("Hibernate"))
               .andExpect(jsonPath("$[1].description").value("Curso de hibernate"))
               .andExpect(jsonPath("$[2].title").value("Spring"))
               .andExpect(jsonPath("$[2].description").value("Curso de spring"));
    }

    @Test
    void publishCourse__should_call_publish_port_and_return_no_content() throws Exception {
        Long courseId = 1L;

        mockMvc.perform(post("/course/{id}/publish", courseId)
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());

        verify(publishCourseUseCase, times(1)).publish(courseId);
    }
}



