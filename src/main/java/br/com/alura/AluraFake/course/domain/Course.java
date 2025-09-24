package br.com.alura.AluraFake.course.domain;

import br.com.alura.AluraFake.task.domain.Task;
import br.com.alura.AluraFake.user.domain.User;
import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.alura.AluraFake.task.domain.TaskValidator.*;
import static br.com.alura.AluraFake.task.domain.TaskValidator.validateSequentialOrder;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String title;
    private String description;
    @ManyToOne
    private User instructor;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime publishedAt;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @Deprecated
    public Course(){}

    public Course(String title, String description, User instructor) {
        Assert.isTrue(instructor.isInstructor(), "Usuario deve ser um instrutor");
        this.title = title;
        this.instructor = instructor;
        this.description = description;
        this.status = Status.BUILDING;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getInstructor() {
        return instructor;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void addTask(Task task) {

        validateCourseStatus(this);
        validateDuplicateStatement(this, task.getStatement());
        validateOrderNotNegative(task.getOrder());
        validateSequentialOrder(getTaskOrderList(), task.getOrder());

        if(this.tasks.stream().anyMatch(t -> t.getOrder().equals(task.getOrder()))) {

            this.tasks.stream()
                      .filter(t -> t.getOrder() >= task.getOrder())
                      .forEach(t -> t.setOrder(t.getOrder() + 1));

        }

        this.tasks.add(task);
    }

    public List<Task> getTasks() {

        return tasks;
    }

    public List<Integer> getTaskOrderList() {

        return this.getTasks()
                     .stream()
                     .map(Task::getOrder)
                     .toList();
    }
}
