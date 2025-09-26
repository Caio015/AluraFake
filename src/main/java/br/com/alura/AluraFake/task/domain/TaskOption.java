package br.com.alura.AluraFake.task.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "Task_option")
public class TaskOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_text", nullable = false)
    private String option;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
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
}