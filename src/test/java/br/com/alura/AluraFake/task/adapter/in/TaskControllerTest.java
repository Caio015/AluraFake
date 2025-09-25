package br.com.alura.AluraFake.task.adapter.in;

import br.com.alura.AluraFake.course.domain.Course;
import br.com.alura.AluraFake.task.adapter.in.DTO.TaskDTO;
import br.com.alura.AluraFake.task.adapter.in.DTO.TaskOptionDTO;
import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.task.domain.TaskFactory;
import br.com.alura.AluraFake.task.domain.TaskOption;
import br.com.alura.AluraFake.task.domain.Type;
import br.com.alura.AluraFake.task.port.in.CreateMultipleChoiceTaskUseCase;
import br.com.alura.AluraFake.task.port.in.CreateOpenTextTaskUseCase;
import br.com.alura.AluraFake.task.port.in.CreateSingleChoiceTaskUseCase;
import br.com.alura.AluraFake.user.domain.Role;
import br.com.alura.AluraFake.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private CreateOpenTextTaskUseCase openTextUseCase;
    @MockBean private CreateSingleChoiceTaskUseCase singleChoiceUseCase;
    @MockBean private CreateMultipleChoiceTaskUseCase multipleChoiceUseCase;

    @Autowired private ObjectMapper objectMapper;

    private final User instructor = new User("Paulo", "paulo@alura.com", Role.INSTRUCTOR);

    @Test
    void newOpenTextTask__should_return_created_when_request_is_valid() throws Exception {

        Course course = new Course("Java", "Primeiros Passos", instructor);

        TaskDTO dto = new TaskDTO(1L, "Qual a pergunta?", 1, new ArrayList<>());

        Task task = TaskFactory.createTask(course, dto.getStatement(), dto.getOrder(), Type.OPEN_TEXT);

        when(openTextUseCase.execute(dto.getCourseId(), dto.getStatement(), dto.getOrder())).thenReturn(task);

        mockMvc.perform(post("/task/new/opentext").contentType(MediaType.APPLICATION_JSON)
                                                  .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.statement").value("Qual a pergunta?"))
               .andExpect(jsonPath("$.order").value(1))
               .andExpect(jsonPath("$.options").isEmpty());
    }

    @Test
    void newSingleChoiceTask__should_return_created_when_request_is_valid() throws Exception {

        Course course = new Course("Java", "Primeiros Passos", instructor);

        List<TaskOptionDTO> taskOptionsDTO = List.of(new TaskOptionDTO("Java", true),
                                                     new TaskOptionDTO("Python", false),
                                                     new TaskOptionDTO("HTML", false));

        TaskDTO dto = new TaskDTO(1L, "Qual a pergunta?", 1, taskOptionsDTO);

        Task task = TaskFactory.createTask(course, dto.getStatement(), dto.getOrder(), Type.SINGLE_CHOICE);

        List<TaskOption> taskOptions = dto.getOptions()
                                          .stream()
                                          .map(opt -> new TaskOption(opt.getOption(), opt.getCorrect()))
                                          .toList();
        task.addOptions(taskOptions);

        when(singleChoiceUseCase.execute(eq(dto.getCourseId()),
                                         eq(dto.getStatement()),
                                         eq(dto.getOrder()),
                                         anyList())).thenReturn(task);

        mockMvc.perform(post("/task/new/singlechoice").contentType(MediaType.APPLICATION_JSON)
                                                      .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.statement").value("Qual a pergunta?"))
               .andExpect(jsonPath("$.order").value(1))
               .andExpect(jsonPath("$.type").value("SINGLE_CHOICE"))
               .andExpect(jsonPath("$.options[0].option").value("Java"))
               .andExpect(jsonPath("$.options[0].correct").value(true))
               .andExpect(jsonPath("$.options[1].option").value("Python"))
               .andExpect(jsonPath("$.options[1].correct").value(false))
               .andExpect(jsonPath("$.options[2].option").value("HTML"))
               .andExpect(jsonPath("$.options[2].correct").value(false));
    }

    @Test
    void newMultipleChoiceTask__should_return_created_when_request_is_valid() throws Exception {

        Course course = new Course("Java", "Primeiros Passos", instructor);

        List<TaskOptionDTO> taskOptionsDTO = List.of(new TaskOptionDTO("Java", true),
                                                     new TaskOptionDTO("Python", true),
                                                     new TaskOptionDTO("HTML", false));

        TaskDTO dto = new TaskDTO(1L, "Qual a pergunta?", 1, taskOptionsDTO);

        Task task = TaskFactory.createTask(course, dto.getStatement(), dto.getOrder(), Type.MULTIPLE_CHOICE);

        List<TaskOption> taskOptions = dto.getOptions()
                                          .stream()
                                          .map(opt -> new TaskOption(opt.getOption(), opt.getCorrect()))
                                          .toList();
        task.addOptions(taskOptions);

        when(multipleChoiceUseCase.execute(eq(dto.getCourseId()),
                                           eq(dto.getStatement()),
                                           eq(dto.getOrder()),
                                           anyList())).thenReturn(task);

        mockMvc.perform(post("/task/new/multiplechoice").contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.statement").value("Qual a pergunta?"))
               .andExpect(jsonPath("$.order").value(1))
               .andExpect(jsonPath("$.type").value("MULTIPLE_CHOICE"))
               .andExpect(jsonPath("$.options[0].option").value("Java"))
               .andExpect(jsonPath("$.options[0].correct").value(true))
               .andExpect(jsonPath("$.options[1].option").value("Python"))
               .andExpect(jsonPath("$.options[1].correct").value(true))
               .andExpect(jsonPath("$.options[2].option").value("HTML"))
               .andExpect(jsonPath("$.options[2].correct").value(false));
    }
}