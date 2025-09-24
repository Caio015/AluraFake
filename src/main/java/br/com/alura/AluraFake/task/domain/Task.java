package br.com.alura.AluraFake.task.domain;

import br.com.alura.AluraFake.course.domain.Course;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(length = 255)
    private String statement;

    @Column(name = "task_order", nullable = false)
    private Integer order;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskOption> options = new ArrayList<>();

    protected Task(Course course, String statement, Integer order) {

        this.course = course;
        this.statement = statement;
        this.order = order;
    }

    public Task() {

    }

    public Long getId() {

        return id;
    }

    public Course getCourse() {

        return course;
    }

    public String getStatement() {

        return statement;
    }

    public void setOrder(Integer order) {

        this.order = order;
    }

    public Integer getOrder() {

        return order;
    }

    public Type getType() {

        return type;
    }

    public void setType(Type type) {

        this.type = type;
    }

    public void addOptions(List<TaskOption> options) {

        this.options.addAll(options);
    }

    public List<TaskOption> getOptions() {

        return options;
    }
}