package org.neit.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String address;
    private int experience;

    private float min_wage;
    private float max_wage;
    private float wage;

    private LocalDate deadline;

    @ManyToOne
    private Company company;

    @ManyToMany
    private Set<City> cities;

    @ManyToOne
    private User user;
    private boolean isActive = true;
    public Job() {
    }

    public Job(Integer id, String name, String description, String address, int experience, float min_wage, float max_wage, float wage, LocalDate deadline, Company company, Set<City> cities, User user, boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.experience = experience;
        this.min_wage = min_wage;
        this.max_wage = max_wage;
        this.wage = wage;
        this.deadline = deadline;
        this.company = company;
        this.cities = cities;
        this.user = user;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public float getMin_wage() {
        return min_wage;
    }

    public void setMin_wage(float min_wage) {
        this.min_wage = min_wage;
    }

    public float getMax_wage() {
        return max_wage;
    }

    public void setMax_wage(float max_wage) {
        this.max_wage = max_wage;
    }

    public float getWage() {
        return wage;
    }

    public void setWage(float wage) {
        this.wage = wage;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
