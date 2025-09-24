package br.com.alura.AluraFake.task.domain;

import jakarta.persistence.*;

@Entity
public class TaskOption {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String option;

    @Column(nullable = false)
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public TaskOption(String text, boolean isCorrect, Task task) {

        this.option = text;
        this.isCorrect = isCorrect;
        this.task = task;
    }

    public TaskOption() {

    }

    public Long getId() {

        return id;
    }

    public String getOption() {

        return option;
    }

    public boolean isCorrect() {

        return isCorrect;
    }

    public Task getTask() {

        return task;
    }
}