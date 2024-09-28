package org.neit.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private User user;
    private LocalDate createdDate = LocalDate.now();

    @ManyToOne
    private Job job;
    private String resumePath;
    public Resume(){}

    public Resume(Integer id, User user, LocalDate createdDate, String resumePath) {
        this.id = id;
        this.user = user;
        this.createdDate = createdDate;
        this.resumePath = resumePath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }
}
