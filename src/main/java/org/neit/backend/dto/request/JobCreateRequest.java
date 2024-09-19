package org.neit.backend.dto.request;

import java.time.LocalDate;
import java.util.Set;

public class JobCreateRequest {
    private String name;
    private String description;
    private String address;
    private int experience;

    private float min_wage;
    private float max_wage;
    private float wage;

    private LocalDate deadline;

    private String company;

    private Set<String> cities;

    private String user;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Set<String> getCities() {
        return cities;
    }

    public void setCities(Set<String> cities) {
        this.cities = cities;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
